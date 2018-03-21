package com.somnus.accessModifier;

/**
 * java只有按值传递，没有按引用传递
 * @Title: PassByValueDemo.java 
 * @Package com.somnus.accessModifier 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月11日 下午12:58:18 
 * @version V1.0
 */
public class PassByValueDemo {
    int in = 5;
	String str = new String("good");   
	char[] ch = { 'a', 'b', 'c',97 };   
	public void change(int in, String str, char[] ch) {   
		in = 10;
        str = "test ok";   
        ch[0] = 'g';   
    }   
	public static void main(String args[]){   
		PassByValueDemo ex = new PassByValueDemo();   
        ex.change(ex.in,ex.str, ex.ch);   
        System.out.print(ex.in+" and "+ex.str + " and ");   
        System.out.println(ex.ch);   
    }   
}
