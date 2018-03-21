package com.somnus.reflect;

public class People implements China{
	private String sex;

	public People(String sex) {
		super();
		this.sex = sex;
	}

	public People() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void sayChina() {
		System.out.println("hello ,china"); 
	}

	public void sayHello(String name, int age) {
		System.out.println(name+"  "+age); 
	} 
	public String say(String name, int age) {
		return name+"  "+age;
	} 	
}
