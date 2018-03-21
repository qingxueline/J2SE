package com.somnus.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @Description: TODO
 * @author Somnus
 * @date 2015年12月16日 下午2:37:26
 * @version V1.0
 */
public class HttpClient {
	
    @Test
    public void doJsonPost(){
    	String url = "http://restful.wozsz.com/service/com.somnus.resource.RestfulResource/getAccount";
        Map<String,String> param = new HashMap<String, String>();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println(HttpJSONUtils.doJsonPost(url, param));
    }
    
    @Test
    public void doXmlPost(){
    	String url = "http://restful.wozsz.com/service/com.somnus.resource.RestfulResource/getAccount2";
        Map<String,String> param = new HashMap<String, String>();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println(HttpXMLUtils.doXmlPost(url, param));
    }
    
    @Test
    public void doPathParamGet() {
        String url = "http://restful.wozsz.com/service/com.somnus.resource.RestfulResource/getAccount3/admin/123456";
        System.out.println(HttpUtils.doGet(url));
    }

    @Test
    public void doQueryParamGet() {
        String url = "http://restful.wozsz.com/service/com.somnus.resource.RestfulResource/getAccount4";
        Map<String,String> param = new HashMap<String, String>();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println(HttpUtils.doGet(url, param));
    }

    @Test
    public void doFormParamPost(){
        String url = "http://restful.wozsz.com/service/com.somnus.resource.RestfulResource/getAccount5";
        Map<String,String> param = new HashMap<String, String>();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println(HttpUtils.doPost(url, param));
    }
}
