package com.somnus.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpJSONUtils {
	
	private transient static Logger log = LoggerFactory.getLogger(HttpJSONUtils.class);
	
	public static String doJsonPost(String url, Map<String,String> param){
		String resultString = "";
		if(param!=null && !param.isEmpty()){
			JSONObject jsonObject = new JSONObject();
			for(String key :param.keySet()){
				jsonObject.put(key, param.get(key));
			}
			String json = jsonObject.toString();
			System.out.println("请求报文JSON:" + json);
			resultString = doJsonPost(url,json);
		} else{
			resultString = doJsonPost(url,"");
		}
		return resultString;
	}
	
	
	public static String doJsonPost(String url, String json){
		//创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
        	// 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json,ContentType.APPLICATION_JSON));
            
            // 开始执行http请求
            long startTime = System.currentTimeMillis();  
            httpResponse = httpclient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            
            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();  
            log.info("statusCode:" + statusCode);  
            log.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            
            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity,Charset.forName("UTF-8"));
            
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
            	throw new RuntimeException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode,resultString));
            } 
        } catch (ClientProtocolException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally{
            try {
            	if(httpResponse != null){
            		httpResponse.close();
            	}
            	httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
        }
		return resultString;
    }
	
	
}
