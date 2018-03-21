package com.somnus.thread;
/**
 * 
 * @Title: Thread2Test.java 
 * @Package com.somnus.thread 
 * @Description: 第二种实现线程的方式
 * @author Somnus
 * @date 2015年4月26日 下午9:18:12 
 * @version V1.0
 */
public class Thread2Test{
	public static void main(String[] args){
		new Thread(new Runnable(){
		    @Override
			public void run(){
				for(int i = 0; i<100;i++){
					System.out.println("++++++++++++"+i+"++++++++++++");
				}
			}
		}).start();
		
		new Thread(new Runnable(){
		    @Override
			public void run(){
				for(int i = 0; i<100;i++){
					System.out.println("**************"+i+"**************");
				}
			}
		}).start();
		
	}
}
