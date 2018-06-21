package com.somnus.thread.multi_002.conn008;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 此段代码的目的是实现T1线程不断向list里面添加元素，当元素达到一定数量后被T2线程检测到。从而终止T2。
 *
 * 此段代码没有使用 wait 和 notify .T2线程是使用一个死循环来检测list里面的值。这样的内存消耗是不可取的。
 *
 * 所以后面的程序，我们需要使用 wait 和 notify 来解决此问题。
 */
public class ListAdd1 {

	private volatile static List list = new ArrayList();	
	
	public void add(){
		list.add("bjsxt");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final ListAdd1 list1 = new ListAdd1();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for(int i = 0; i <10; i++){
						list1.add();
						System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
						Thread.sleep(500);
					}	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					if(list1.size() == 5){
						System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
						throw new RuntimeException();
					}
				}
			}
		}, "t2");		
		
		t1.start();
		t2.start();
	}

}
