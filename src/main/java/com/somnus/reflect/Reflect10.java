package com.somnus.reflect;

import java.lang.reflect.Method;

public class Reflect10 {

	/**
	 * 通过反射调用其他类中的方法
	 * @param args
	 */
	public static void main(String[] args) {
		Class<?> demo = null;
		try {
			demo = Class.forName("com.somnus.reflect.People");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 调用Person类中的sayChina方法
			Method method = demo.getMethod("sayChina");
			method.invoke(demo.newInstance());
			// 调用Person的sayHello方法
			method = demo.getMethod("sayHello", new Class[]{String.class, int.class});
			method.invoke(demo.newInstance(), new Object[]{"Rollen", 20} );
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
