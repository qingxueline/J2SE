package com.somnus.http.http2;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 功能说明：http json 请求
 * Created by lyl on 2017/6/30 0030.
 */
public class HttpJSONUtils {

    private transient static Logger log = LoggerFactory.getLogger(HttpJSONUtils.class);

    public static HttpEntityResponse doJsonPost(String url, Map<String, String> param) {
        if (param != null && !param.isEmpty()) {
            return doJsonPost(url, new Gson().toJson(param), null);
        } else {
            return doJsonPost(url, "", null);
        }
    }

    public static HttpEntityResponse doJsonPost(String url, Map<String, String> param, Map<String, String> headers) {
        if (param != null && !param.isEmpty()) {
            return doJsonPost(url, new Gson().toJson(param), headers);
        } else {
            return doJsonPost(url, "", headers);
        }
    }


    public static HttpEntityResponse doJsonPost(String url, String json, Map<String, String> headers) {
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
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

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
