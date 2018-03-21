package com.somnus.reflect.reflect;

import java.lang.reflect.Method;

/**
 * 
 * @Title: InvokeTest.java 
 * @Package com.somnus.reflect.reflect 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月10日 上午9:32:00 
 * @version V1.0
 */
public class InvokeTest {
	
	public int add(int param1, int param2){
		return param1+param2;
	}
	
	public String echo(String msg){
		return "hello:"+msg;
	}
	
	public static void main(String[] args) throws Exception{
		//会获得InvokeTest类所对应Class对象
		
		Class<?> clazz = InvokeTest.class;
		
		Object invokeTest = clazz.newInstance();
		
		Method addMethod = clazz.getMethod("add", new Class[]{int.class,int.class});
		
		Object result = addMethod.invoke(invokeTest, new Object[]{100,100});
		
		System.out.println((Integer)result);
		
		Method echoMethod = clazz.getMethod("echo", new Class[]{String.class});
		
		Object result2 = echoMethod.invoke(invokeTest, new Object[]{"yourself"});
		
		System.out.println((String)result2);
		
		
	}
}
