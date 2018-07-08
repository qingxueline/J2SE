package com.somnus.thread.multi_006.lock021;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class UseReentrantReadWriteLock {

	//读写锁对象
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	//读锁
	private ReadLock readLock = rwLock.readLock();
	//写锁
	private WriteLock writeLock = rwLock.writeLock();
	
	public void read(){
		try {
			readLock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入...");
			Thread.sleep(3000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}
	
	public void write(){
		try {
			writeLock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入...");
			Thread.sleep(3000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final UseReentrantReadWriteLock urrw = new UseReentrantReadWriteLock();
		
		Thread t1 = new Thread(() -> urrw.read(), "t1");
		Thread t2 = new Thread(() -> urrw.read(), "t2");
		Thread t3 = new Thread(() -> urrw.write(), "t3");
		Thread t4 = new Thread(() -> urrw.write(), "t4");

		//读读共享
		t1.start();
		t2.start();

		//读写互斥
//		t1.start(); // R
//		t3.start(); // W

		//写写互斥
//		t3.start();
//		t4.start();

	}
}
