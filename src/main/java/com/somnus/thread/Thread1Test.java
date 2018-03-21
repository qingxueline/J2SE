package com.somnus.thread;
/**
 * 
 * @Title: Thread1Test.java 
 * @Package com.somnus.thread 
 * @Description: 第一种实现线程的方式
 * @author Somnus
 * @date 2015年4月26日 下午9:17:24 
 * @version V1.0
 */
public class Thread1Test{
	public static void main(String[] args){
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		t1.start();
		t2.start();
	}
}
class Thread1 extends Thread{
    @Override
	public void run(){
		for(int i = 0; i<100;i++){
			System.out.println("------------"+i+"------------");
		}
	}
}
class Thread2 extends Thread{
    @Override
	public void run(){
		for(int i = 0; i<100;i++){
			System.out.println("************"+i+"*************");
		}
	}
}