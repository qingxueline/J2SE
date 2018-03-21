package com.somnus.reflect;

public class Reflect03 {
	/**通过Class实例化其他类的对象
	 * 通过无参构造实例化对象
	 * @param args
	 */
	public static void main(String[] args) {
		Class<?> demo = null;
		try {
			demo = Class.forName("com.somnus.reflect.Person");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
		
		Person person = null;
		try {
			person = (Person)demo.newInstance();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		person.setName("jay");
		person.setAge(11);
		System.out.println(person);
	}

}