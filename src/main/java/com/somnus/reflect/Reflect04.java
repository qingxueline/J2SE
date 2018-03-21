package com.somnus.reflect;


public class Reflect04 {

	/**
	 * 通过Class调用其他类中的构造函数 
	 * （也可以通过这种方式通过Class创建其他类的对象）
	 * @param args
	 */
	public static void main(String[] args) {
		Class<?> demo = null;
		try {
			demo = Class.forName("com.somnus.reflect.Person");
		} 
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}		
		
        try{
        	Person person = (Person)demo.getConstructor(new Class[]{String.class}).newInstance(new Object[]{"Rollen"});
        	System.out.println(person);
        }
        catch(Exception e){
        	e.printStackTrace();      
        }
	}
}
