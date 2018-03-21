package com.somnus.thread;
/**
 * Java中的每个对象都有一个锁（lock）或者叫做监视器（monitor），当访问某个对象的synchronized方法时，
 * 表示将该对象上锁，此时其它任何线程都无法再去访问该synchronized方法了，直到之前的那个线程执行方法完毕后（或者抛出了异常）
 * 那么将该对象的锁释放掉，其它线程才有可能再去访问该synchronized方法
 * 
 * 如果一个对象有多个synchronized方法，某一时刻某个线程已经进入到了某个synchronized方法，
 * 那么该方法没有执行完毕前，其它线程是无法访问该对象的任何synchronized方法的
 * 
 */
public class ThreadTest11{
	public static void main(String[] args){
		final Outputter output = new Outputter();

		new Thread(){
			public void run(){
				output.output("abcdefghijklmnopqrstuvwxyz");
			};
		}.start();

		new Thread(){
			public void run(){
				output.output("0123456789");
			};
		}.start();
	}
}

class Outputter{
	public void output(String name){
		// TODO 为了保证对name的输出不是一个原子操作，这里逐个输出name的每个字符
		for (int i = 0; i < name.length(); i++){
			System.out.print(name.charAt(i));
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
		}
	}

	public void output2(String name){
		synchronized (this){
			for (int i = 0; i < name.length(); i++){
				System.out.print(name.charAt(i));
			}
		}
	}
	
	public synchronized void output3(String name){
		// TODO 为了保证对name的输出不是一个原子操作，这里逐个输出name的每个字符
		for (int i = 0; i < name.length(); i++){
			System.out.print(name.charAt(i));
		}
	}
}