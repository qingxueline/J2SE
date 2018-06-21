package com.somnus.thread.multi_002.conn008;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * wait notfiy 方法，wait释放锁，notfiy不释放锁
 *
 * @author alienware
 */
public class ListAdd2 {
	private volatile static List list = new ArrayList();

	public void add() {
		list.add("bjsxt");
	}

	public int size() {
		return list.size();
	}

	public static void main(String[] args) {

		final ListAdd2 list2 = new ListAdd2();

		// 1 实例化出来一个 lock
		// 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用，使用同一个对象锁
		final Object lock = new Object();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
					for (int i = 0; i < 10; i++) {
						list2.add();
						System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
						Thread.sleep(500);
						if (list2.size() == 5) {
							System.out.println("已经发出通知..");
//							countDownLatch.countDown();
							//唤醒等待的程序，但是并没有把锁释放。notify方法是不释放锁的，要等for循环完10次才会释放锁。所以这里有一个弊端，就是通知不实时的问题，必须要等T1执行完才会回去执行T2。这个问题可以使用CountDownLatch解决
							lock.notify();
						}
					}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
				if (list2.size() != 5) {
					try {
						System.out.println("t2进入...");
						lock.wait();
//						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
				throw new RuntimeException();
				}
			}
		}, "t2");

		//T2线程先执行，然后获得了锁。因为T2线中有lock.wait()，所以执行途中就会释放锁，同时进入等待状态。
		t2.start();

		//T1线程开始执行，因为上面T2已经释放了锁，所以此时T1会拿到锁，进入添加元素操作。添加到第五个元素的时候，因为有lock.notify()代码。所以会唤醒等待的程序，但此时并不会马上释放锁。
		t1.start();

	}

}
