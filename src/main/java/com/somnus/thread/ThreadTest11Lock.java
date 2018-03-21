package com.somnus.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 主要相同点:lock能完成synchronized所实现的所有功能
 * 主要不同点:lock有比synchronized更精确的线程语义和更好的性能.synchronized会自动释放锁,
 * 而Lock一定要求程序员手工释放,并且必须在finally从句中释放.
 * 
 * 使用synchronized代码块，可以只对需要同步的代码进行同步，这样可以大大的提高效率。
 * 小结：
 * 使用synchronized 代码块相比方法有两点优势：
 * 1、可以只对需要同步的使用
 * 2、与wait()/notify()/nitifyAll()一起使用时，比较方便
 * 
 * 但是由于synchronized是在JVM层面实现的，因此系统可以监控锁的释放与否，而ReentrantLock使用代码实现的，系统无法自动释放锁，
 * 需要在代码中finally子句中显式释放锁lock.unlock();
 * 在并发量比较小的情况下，使用synchronized是个不错的选择，但是在并发量比较高的情况下，
 * 其性能下降很严重，此时ReentrantLock是个不错的方案。
 */
public class ThreadTest11Lock{
	public static void main(String[] args){
		final Outputter1 output = new Outputter1();

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

class Outputter1{
    private Lock lock = new ReentrantLock();// 锁对象
    
	public void output(String name){
        lock.lock();// 得到锁  
        try {  
            for(int i = 0; i < name.length(); i++) {  
                System.out.print(name.charAt(i));  
            }  
        } finally {  
            lock.unlock();// 释放锁  
        }  
	}
}