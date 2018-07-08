package com.somnus.thread.multi_006.lock020;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UseCondition {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void method1(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
			Thread.sleep(3000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");

			//使当前线程等待它暗示或 interrupted，等待被其他线程唤醒
			condition.await();	// Object wait
			System.out.println("当前线程：" + Thread.currentThread().getName() +"继续执行...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
			Thread.sleep(3000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");

			//唤醒一个等待线程。
			condition.signal();//Object notify
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final UseCondition uc = new UseCondition();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				uc.method1();
			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				uc.method2();
			}
		}, "t2");


		t1.start();
		t2.start();
	}
	
	
	
}
