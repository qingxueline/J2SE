package com.somnus.reflect.reflect;

import java.lang.reflect.Method;

/**
 * 
 * @Title: DumpMethods.java 
 * @Package com.somnus.reflect.reflect 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月10日 上午9:32:23 
 * @version V1.0
 */
public class DumpMethods{

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException{

		Class<?> clazz = Class.forName(args[0]);

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods){

			System.out.println(method);

		}

	}

}
