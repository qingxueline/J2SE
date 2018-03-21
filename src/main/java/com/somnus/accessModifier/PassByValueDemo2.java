package com.somnus.accessModifier;

/**
 * java只有按值传递，没有按引用传递
 * @Title: PassByValueDemo2.java 
 * @Package com.somnus.accessModifier 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月11日 下午12:57:42 
 * @version V1.0
 */
public class PassByValueDemo2 {
    int num = 9;
    String str = "love";
	char[] ch = {'h','e','l','l','o'};
	void change(int num,char[] ch,String str){
		num = 10;
		ch[0] = 'm';
		ch = new char[]{'w','o','r','l','d'};
		ch[0] = 'n';
		str = "you";
	}
	public static void main(String[] args) {
		PassByValueDemo2 t = new PassByValueDemo2();
		t.change(t.num,t.ch,t.str);
		System.out.print("num:"+t.num+" str:"+t.str+" ch:");
		System.out.println(t.ch);
	}
}
