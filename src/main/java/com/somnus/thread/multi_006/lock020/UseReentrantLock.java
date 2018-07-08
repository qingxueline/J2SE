package com.somnus.thread.multi_006.lock020;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {
	
	private Lock lock = new ReentrantLock();
	
	public void method1(){
		try {
			//获取锁
			lock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method1..");
			Thread.sleep(1000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method1..");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//释放锁,如果不释放锁T1或者T2会有其中一个线程进不来
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			//获取锁
			lock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method2..");
			Thread.sleep(2000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method2..");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//释放锁,如果不释放锁T1或者T2会有其中一个线程进不来
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {

		final UseReentrantLock ur = new UseReentrantLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				ur.method1();
				ur.method2();
			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				ur.method1();
				ur.method2();
			}
		}, "t2");

		//T1线程启动
		t1.start();
		t2.start();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
