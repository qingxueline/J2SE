package com.somnus.reflect;

public class Reflect05 {
	public static void main(String[] args) {
		Class<?> demo=null;
		try{
			demo=Class.forName("com.somnus.reflect.People"); 
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		//保存所有的接口        
		Class<?> intes[]=demo.getInterfaces(); 
		
		for (int i = 0; i < intes.length; i++) {
			System.out.println("实现的接口   "+intes[i].getName());  
		}
	}

}
