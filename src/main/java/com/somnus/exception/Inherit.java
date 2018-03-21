package com.somnus.exception;

public class Inherit {
	public static void main(String[] args) {
		Children children = new Children();
		System.out.println(children.getCause());
	}
}
class Parent{
	private Parent cause = this;
	public Parent() {
		super();
	}
	public Parent getCause() {
		return cause;
	}
	@Override
	public String toString(){
		return "Parent";
	}
}
class Children extends Parent{
	public Children(){
		super();
	}
	@Override
	public String toString(){
		return "Children";
	}
}