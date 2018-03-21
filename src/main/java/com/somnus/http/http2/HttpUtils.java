package com.somnus.http.http2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：http普通请求
 * Created by lyl on 2017/6/30 0030.
 */
public class HttpUtils {

    private transient static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static HttpEntityResponse doGet(String url, Map<String, String> headers) {
        return doGet(url, null, headers);
    }

    public static HttpEntityResponse doGet(String url) {
        return doGet(url, null, null);
    }

    public static HttpEntityResponse doGet(String url, Map<String, String> param, Map<String, String> headers) {
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            //创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null && !param.isEmpty()) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建httpGet请求
            HttpGet httpGet = new HttpGet(uri);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 开始执行http请求
            long startTime = System.currentTimeMillis();
            httpResponse = httpclient.execute(httpGet);
            long endTime = System.currentTimeMillis();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API花费时间(单位：毫秒)：" + (endTime - startTime));

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            // 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            resultString.replaceAll("\r", "");
            HttpEntityResponse response = new HttpEntityResponse(httpEntity, statusCode, resultString);
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                response.setRuntimeExceptionMessage(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
            return response;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static HttpEntityResponse doPost(String url, Map<String, String> param) {
        return doPost(url, param, null);
    }

    public static HttpEntityResponse doPost(String url, Map<String, String> param, Map<String, String> headers) {
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {

            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (param != null && !param.isEmpty()) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    params.add(new BasicNameValuePair(key, param.get(key)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            }

            // 开始执行http请求
            long startTime = System.currentTimeMillis();
            httpResponse = httpclient.execute(httpPost);
            long endTime = System.currentTimeMillis();


            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API花费时间(单位：毫秒)：" + (endTime - startTime));

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            HttpEntityResponse response = new HttpEntityResponse(httpEntity, statusCode, resultString);
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                response.setRuntimeExceptionMessage(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
            return response;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return null;
    }


}
