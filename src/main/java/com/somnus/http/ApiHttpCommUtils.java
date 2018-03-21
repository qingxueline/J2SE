package com.somnus.http;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 接口请求核心处理类：
 * 		GET请求方法
 * 		POST请求方法
 * 		参数编码方法
 * 		设置响应超时时间方法
 * 		将响应结果转为json对象方法
 * @author huangk
 *
 */
public class ApiHttpCommUtils {
	private static Logger logger = Logger.getLogger(ApiHttpCommUtils.class);
	public static final String EXCEPTION_NOT_200_OK = "Status code is not 200";

	private static int CONN_TIMETOUT = 17 * 1000;
	private static int READ_TIMETOUT = 17 * 1000;

	/**
	 * 设置连接超时时间，单位为毫秒，缺省值为15000毫米，可通过此方法进行重新设置。
	 * 
	 * @param cONN_TIMETOUT
	 *            连接超时时间，单位为毫秒
	 */
	public static void setCONN_TIMETOUT(int cONN_TIMETOUT) {
		CONN_TIMETOUT = cONN_TIMETOUT;
	}

	/**
	 * 设置读取响应的超时时间，单位为毫秒，缺省值为15000毫米，可通过此方法进行重新设置。
	 * 
	 * @param rEAD_TIMETOUT
	 *            响应的超时时间，单位为毫秒
	 */
	public static void setREAD_TIMETOUT(int rEAD_TIMETOUT) {
		READ_TIMETOUT = rEAD_TIMETOUT;
	}

	/**
	 * 如果URL的参数含有中文的URL，需要进行编码的转换
	 * 
	 * @param source
	 *            给出url的参数
	 * @return 整理后的值
	 */
	public static String urlParamEncodeUTF8(String source) {
		try {
			return URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			logger.error("urlEncodeUTF8() error :" + e.toString());
			return null;
		}
	}

	/**
	 * 简单的Http Get接口调用，适用于通过HTTP Get请求某个能力，如sms.gd114，一般返回200OK表示正常，返回400表示参数错误，
	 * 返回403表示禁止访问，返回500表示处理失败。
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @return status code
	 * @throws Exception
	 *             连接异常
	 */
	public static String simpleHttpGetRequest(String requestUrl, String[] requestPropertiesName, String[] requestPropertiesValue) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setConnectTimeout(CONN_TIMETOUT);
		conn.setReadTimeout(READ_TIMETOUT);
		conn.setRequestMethod("GET");
		if (requestPropertiesName != null) {
			for (int i = 0; i < requestPropertiesName.length; i++) {
				conn.setRequestProperty(requestPropertiesName[i], requestPropertiesValue[i]);
			}
		}
		conn.connect();
		try {
//			int statusCode = conn.getResponseCode();
			// 读返回内容
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} catch (Exception e) {
			logger.error("Received HTTP response error : " + e.toString());
			return null;
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * 发送HTTP请求。
	 * 
	 * @param requestUrl
	 *            http的请求URL地址
	 * @param requestMethod
	 *            请求方法，即GET或者POST
	 * @param requestPropertiesName
	 *            设置请求的消息头的名字
	 * @param requestPropertiesValue
	 *            设置请求的消息头的值，顺序和名字一一对应
	 * @param body
	 *            请求的内容，如无为null
	 * @return Http 200OK响应的body，如果非200OK，则抛出异常，具体定义见EXCEPTION_NOT_200_OK
	 * @throws Exception
	 *             诸如网络不可达，网络连接超时，非200 OK等
	 */
	public static String sendHttpRequest(String requestUrl, String requestMethod, String[] requestPropertiesName,
			String[] requestPropertiesValue, String body) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoInput(true);// 据说，可以使用conn.getInputStream().read();不设置可能会报错
		conn.setDoOutput(true); // 据说，可以使用conn.getOutputStream().write();不设置可能会报错
		conn.setUseCaches(false); // 由于是程序处理，非浏览器，没有什么reload，所以不需要cache了
		conn.setConnectTimeout(CONN_TIMETOUT);
		conn.setReadTimeout(READ_TIMETOUT);

		if (requestPropertiesName != null) {
			for (int i = 0; i < requestPropertiesName.length; i++) {
				conn.setRequestProperty(requestPropertiesName[i], requestPropertiesValue[i]);
			}
		}

		requestMethod = requestMethod.toUpperCase();
		conn.setRequestMethod(requestMethod);

		if (body == null) {
			conn.connect();
		} else { // body != null
			try { // OutputStream实现AutoCloseable
				OutputStream os = conn.getOutputStream();
				os.write(body.getBytes("utf-8"));
			} catch (Exception e) {
				conn.disconnect();
				logger.error("Sent HTTP request error : " + e.toString());
				throw e;
			}
		}
		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			conn.disconnect();
			String state = responseCode + " " + conn.getResponseMessage();
			logger.error("Received non-200OK response : " + state);
			throw new Exception(EXCEPTION_NOT_200_OK, new Throwable(state));
		}

		// 读返回内容
		try {
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			return sb.toString();

		} catch (Exception e) {
			logger.error("Received HTTP response error : " + e.toString());
			throw e;

		} finally {
			conn.disconnect();
		}

	}

	/**
	 * 用于Json作为body的HTTP请求和应答对。
	 * 
	 * @param requestUrl
	 *            请求的URL地址
	 * @param requestMethod
	 *            请求的方法，GET或者POST
	 * @param requestBodyObj
	 *            请求中body的对象，将自动翻译为json
	 * @param classOfT
	 *            响应消息json所对应的java类
	 * @param <T>
	 *            响应消息json所对应的java类，具体在classOfT中定义
	 * @return 返回响应消息body经json翻译而获得的对象，类型在classOfT中定义
	 * @throws Exception
	 *             给出异常，包括非200 OK的响应（具体定义见EXCEPTION_NOT_200_OK）
	 */
	public static <T> T sendHttpRequestForJason(String requestUrl, String requestMethod, Object requestBodyObj,
			Class<T> classOfT) throws Exception {
		String resultJsonStr = null;
		Gson g = new Gson();

		if (requestBodyObj == null) {
			resultJsonStr = sendHttpRequest(requestUrl, requestMethod, null, null, null);
		} else {
			String[] propertiesName = { "Content-Type" };
			String[] propertiesValues = { "application/json;charset=UTF-8" };

			resultJsonStr = sendHttpRequest(requestUrl, requestMethod, propertiesName, propertiesValues,
					g.toJson(requestBodyObj));
		}

		return g.fromJson(resultJsonStr, classOfT);
	}
	
	/**
	 * 用于Json作为body的HTTP请求和应答对。
	 * @param requestUrl
	 * 				请求的URL地址
	 * @param requestMethod
	 * 				请求的方法，GET或者POST
	 * @param requestPropertiesName
	 * 				设置请求的消息头的名字
	 * @param requestPropertiesValue
	 * 				设置请求的消息头的值，顺序和名字一一对应
	 * @param requestBodyObj
	 * 				请求中body的对象，将自动翻译为json
	 * @param classOfT
	 * 				响应消息json所对应的java类
	 * @return		返回响应消息body经json翻译而获得的对象，类型在classOfT中定义	
	 * @throws Exception
	 * 				给出异常，包括非200 OK的响应（具体定义见EXCEPTION_NOT_200_OK）
	 */
	public static <T> T sendHttpRequestForJason(String requestUrl, String requestMethod, String[] requestPropertiesName,
												String[] requestPropertiesValue,Object requestBodyObj,
			Class<T> classOfT) throws Exception {
		String resultJsonStr = null;
		Gson g = new Gson();

		if (requestBodyObj == null) {
			resultJsonStr = sendHttpRequest(requestUrl, requestMethod, null, null, null);
		} else {
//			String[] propertiesName = { "Content-Type" };
//			String[] propertiesValues = { "application/json;charset=UTF-8" };

			resultJsonStr = sendHttpRequest(requestUrl, requestMethod, requestPropertiesName, requestPropertiesValue,
					g.toJson(requestBodyObj));
		}

		return g.fromJson(resultJsonStr, classOfT);
	}

}
