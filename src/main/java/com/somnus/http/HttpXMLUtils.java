package com.somnus.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HttpXMLUtils {
	
	private transient static Logger log = LoggerFactory.getLogger(HttpXMLUtils.class);
	
	public static String doXmlPost(String url, Map<String,String> param){
		String resultString = "";
		if(param!=null && !param.isEmpty()){
			XStream xstream = new XStream();
	        xstream.processAnnotations(param.getClass());
	        xstream.registerConverter(new Converter(){
				@Override
				public boolean canConvert(Class clazz) {
					return AbstractMap.class.isAssignableFrom(clazz); 
				}
				@Override
				public void marshal(Object value,HierarchicalStreamWriter writer,MarshallingContext context) {
					 AbstractMap<String,String> map = (AbstractMap<String,String>) value;
					    for (Entry<String,String> entry : map.entrySet()) {
					        writer.startNode(entry.getKey().toString());
					        writer.setValue(entry.getValue().toString());
					        writer.endNode();
					    }
				}
				@Override
				public Object unmarshal(HierarchicalStreamReader reader,UnmarshallingContext context) {
					Map<String, String> map = new HashMap<String, String>();
		            while(reader.hasMoreChildren()) {
		                reader.moveDown();
		                String key = reader.getNodeName();
		                String value = reader.getValue();
		                map.put(key, value);
		                reader.moveUp();
		            }
		            return map;
				}
	        });
			String xml = xstream.toXML(param);
			System.out.println("请求报文XML:" + xml);
			resultString = doXmlPost(url,xml);
		} else{
			resultString = doXmlPost(url,"");
		}
		return resultString;
	}
	
	public static String doXmlPost(String url, String xml){
		//创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
        	// 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xml,ContentType.APPLICATION_XML));
            
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
