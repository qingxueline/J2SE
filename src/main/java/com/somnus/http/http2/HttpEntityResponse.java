package com.somnus.http.http2;

import org.apache.http.HttpEntity;

/**
 * 功能说明：封装HttpClient响应体内容
 * Created by lyl on 2017/7/8 0008.
 */
public class HttpEntityResponse {
    /*响应体*/
    private HttpEntity entity;
    /*响应状态码*/
    private int statusCode;
    /*应答字符串*/
    private String result;
    /*z错误信息*/
    private String runtimeExceptionMessage;

    public HttpEntityResponse() {
    }

    public HttpEntityResponse(HttpEntity entity, int statusCode, String result) {
        this.entity = entity;
        this.statusCode = statusCode;
        this.result = result;
    }

    public HttpEntityResponse(HttpEntity entity, String runtimeExceptionMessage, String result, int statusCode) {
        this.entity = entity;
        this.runtimeExceptionMessage = runtimeExceptionMessage;
        this.result = result;
        this.statusCode = statusCode;
    }

    public HttpEntity getEntity() {
        return entity;
    }

    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRuntimeExceptionMessage() {
        return runtimeExceptionMessage;
    }

    public void setRuntimeExceptionMessage(String runtimeExceptionMessage) {
        this.runtimeExceptionMessage = runtimeExceptionMessage;
    }
}
