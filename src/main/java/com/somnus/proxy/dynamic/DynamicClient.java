package com.somnus.proxy.dynamic;

import com.somnus.proxy.Hello;
import com.somnus.proxy.HelloImpl;

/** 
 * @Title: DynamicClient.java 
 * @Package com.somnus.proxy.dynamic 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月3日 下午1:33:01 
 * @version V1.0 
 */
public class DynamicClient {

    public static void main(String[] args) {
        /*Hello hello = new HelloImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());
        Hello helloProxy = (Hello) Proxy.newProxyInstance(
            hello.getClass().getClassLoader(),
            hello.getClass().getInterfaces(),
            dynamicProxy
        );
        helloProxy.say("Jack");*/
        
        DynamicProxy dynamicProxy2 = new DynamicProxy(new HelloImpl());
        Hello helloProxy2 = dynamicProxy2.getProxy();
        helloProxy2.say("Jack");
    }

}
