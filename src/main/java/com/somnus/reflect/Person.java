package com.somnus.reflect;

public class Person {
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(String name) {
		super();
		this.name = name;
	}
	public Person(int age) {
		super();
		this.age = age;
	}
	public Person(String name,int age) {
		super();
		this.age = age;
		this.name = name;
	}

	public int age;
	public String name;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return "["+this.name+"||"+this.age+"]";
	}

}
