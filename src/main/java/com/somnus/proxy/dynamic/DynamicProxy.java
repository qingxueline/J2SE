package com.somnus.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/** 
 * @Title: DynamicProxy.java 
 * @Package com.somnus.proxy.dynamic 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月3日 下午1:31:37 
 * @version V1.0 
 */
public class DynamicProxy implements InvocationHandler {
 
    private Object target;
 
    public DynamicProxy(Object target) {
        this.target = target;
    }
 
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            this
        );
    }
 
    private void before() {
        System.out.println("Before");
    }
 
    private void after() {
        System.out.println("After");
    }
}