package com.somnus.reflect;

public class Reflect02 {
	/**
	 * 实例化Class类对象
	 * @param args
	 */
	public static void main(String[] args){
		Class<?> demo1=null;
		Class<?> demo2=null;
		Class<?> demo3=null;
		try {
			demo1=Class.forName("com.somnus.somnus.reflect.Demo");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		demo2=new Demo().getClass();
		demo3=Demo.class;
		System.out.println("类名称"+demo1.getName());
		System.out.println("类名称"+demo2.getName());
		System.out.println("类名称"+demo3.getName());
	}
/**
 * 【运行结果】：
 * 类名称   Reflect.Demo
 * 类名称   Reflect.Demo
 * 类名称   Reflect.Demo
 */
}