package com.somnus.statics;

class Singletion{
	private static Singletion singletion = new Singletion();
	/* 1、类的加载：查找并加载类的二进制数据
	 * 2、连接
	 * 		2.1、验证：确保被加载的类的正确性
	 * 		2.2、准备：为类的【静态变量】分配内存，并将其初始为默认值
	 * 		2.3、解析：把类中的符号引用转换为直接引用
	 * 3、初始化：为类的【静态变量】赋予正确的初始值
	 */
	//在执行完第二步后，进入第三步，如果第一步执行构造方法，静态属性都开始改变值，然而再继续往下初始化的时候，counter2会被重新赋值
	//另外counter3在类进行实例化的时候，开始赋值（没有被赋值就默认值）然后紧接着在构造方法中又被改变
	public static int counter1;
	public static int counter2 = 0;
	
	public int counter3 = 0;//该属性在类的初始化不会有任何动作,类实例化后开始赋值
	
	private Singletion(){
		counter1++;
		counter2++;
		counter3++;
	}
	public static Singletion getInstance(){
		return singletion;
	}
}
public class JVMStaticTest{
	public static void main(String[] args){
		Singletion singletion = Singletion.getInstance();
		System.out.println(singletion.counter1);
		System.out.println(singletion.counter2);
		System.out.println(singletion.counter3);
	}	
}