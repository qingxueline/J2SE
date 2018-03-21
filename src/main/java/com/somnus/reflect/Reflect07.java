package com.somnus.reflect;

import java.lang.reflect.Constructor;

public class Reflect07 {

	/**
	 * 获得其他类中的全部构造函数
	 * @param args
	 */
	public static void main(String[] args) {
        Class<?> demo = null; 
        try{
        	demo = Class.forName("com.somnus.reflect.People");         
        }
        catch (Exception e) {
        	e.printStackTrace();    
        }
        Constructor<?>cons[]=demo.getConstructors();         
        for (int i = 0; i < cons.length; i++) {
        	System.out.println("构造方法：  "+cons[i]);   
        } 
	}
}
