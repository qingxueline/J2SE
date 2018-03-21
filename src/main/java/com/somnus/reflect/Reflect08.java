package com.somnus.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class Reflect08 {
	/**
	 * 获取修饰符
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
		Constructor<?> cons[] = demo.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			Class<?> p[] = cons[i].getParameterTypes();
			System.out.print("构造方法：  ");
			// 权限修饰符
			int mo = cons[i].getModifiers();
			System.out.print(Modifier.toString(mo) + " ");
			//构造方法名字
			String consName=cons[i].getName();
			System.out.print(consName+"(");
			for (int j = 0; j < p.length; ++j){
				//参数类型名字
				String typeName=p[j].getName();
				System.out.print(typeName + " arg" + i);
				if (j < p.length - 1) {
					System.out.print(",");
				}
			}
			System.out.println("){}");
		}
	}
}
