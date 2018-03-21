package com.somnus.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Reflect09 {
	public static void main(String[] args) {
		Class<?> demo = null;
		try {
			demo = Class.forName("com.somnus.reflect.People");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("===============本类属性========================");
		// 取得本类的全部属性
		Field[] field = demo.getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			// 权限修饰符
			int mo = field[i].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = field[i].getType();
			//属性字段名字
			String name=field[i].getName();
			System.out.println(priv + " " + type.getName() + " "+ name + ";");
		}
		System.out.println("===============实现的接口或者父类的属性========================");
		// 取得实现的接口或者父类的属性
		Field[] filed1 = demo.getFields();
		for (int j = 0; j < filed1.length; j++) {
			// 权限修饰符
			int mo = filed1[j].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = filed1[j].getType();
			//属性字段名字
			String name=filed1[j].getName();
			System.out.println(priv + " " + type.getName() + " "+ name + ";");
		}
	}
}
