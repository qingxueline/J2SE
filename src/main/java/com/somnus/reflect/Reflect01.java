package com.somnus.reflect;

public class Reflect01 
{
	/**
	 * 通过一个对象获得完整的包名和类名 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Demo demo=new Demo();
		
		System.out.println(demo.getClass().getName());
		
		System.out.println(Demo.class.getName());
		
		System.out.println(Demo.class.getSimpleName());
	}
}
