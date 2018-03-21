package com.somnus.statics;

import java.util.Random;

import org.junit.Test;

class T{
	public static final int a = 8/2;//不会初始化，【编译的时候就能确定值】
	public static final int aa = new Random().nextInt(100);
	public static int b = 8;
	public static int c ;
	public int d ;
	public T(){
		System.out.println("Constructor is invoked"+this.getClass().getClassLoader());
	}
	
	static {
		System.out.println("********************");
		c = 3;
	}
	
}
public class Test2{
    
    @Test
	public void test1(){
        System.out.println(T.a);
        /*System.out.println(T.b);
        System.out.println(T.c);*/
	}
    @Test
    public void test2(){
        System.out.println(T.aa);
        /*System.out.println(T.b);
        System.out.println(T.c);*/
    }
    
    @Test
    public void test3(){
        T t = new T();
        t.d = 6;
        T.b = 8;
        System.out.println(t.d);
        
        T t2 = new T();
        System.out.println(t2.d);
        System.out.println(t2.b);
    }
}