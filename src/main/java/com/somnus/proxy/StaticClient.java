package com.somnus.proxy;

/** 
 * @Title: StaticClient.java 
 * @Package com.somnus.proxy 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月3日 下午1:29:18 
 * @version V1.0 
 */
public class StaticClient {

    public static void main(String[] args) {
        Hello helloProxy = new HelloProxy(new HelloImpl());
        helloProxy.say("Jack");
    }

}
