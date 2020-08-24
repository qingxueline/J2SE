package com.somnus.proxy.cglib;

import java.io.Serializable;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 使用Cglib动态代理，需要导入Cglib包
 * @description: TODO
 * @author Somnus
 * @date 2015年3月9日 上午9:08:26
 */
public class CglibProxy implements Serializable {
	private static final long serialVersionUID = 1L;

	public Object getProxy(Object target) {
		//Enhancer是cglib中使用频率很高的一个类，它是一个字节码增强器，可以用来为无接口的类创建代理。
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new Handler());
		enhancer.setClassLoader(target.getClass().getClassLoader());
		return enhancer.create();
	}
	
	public Object getProxy(Class<?> clazz){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
        enhancer.setCallback(new Handler());
        enhancer.setInterfaces(new Class[] { Serializable.class });
        return enhancer.create();
	}

	/**
	 * 代理拦截器
	 */
	public static class Handler implements MethodInterceptor{
	    @Override
	    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	        try {
	            // 调用之前
	            doBefore();
	            // 调用原始对象的方法
	            Object result = proxy.invokeSuper(obj, args);
	            // 调用之后
	            doAfter();
	            return result;
	        } catch (Throwable e) {
	            throw e;
	        }
	    }

		private void doBefore() {
			System.out.println("before method invoke");
		}

		private void doAfter() {
			System.out.println("after method invoke");
		}
	}
	
}
