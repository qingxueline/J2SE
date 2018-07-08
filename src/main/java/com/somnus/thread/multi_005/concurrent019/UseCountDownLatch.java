package com.somnus.thread.multi_005.concurrent019;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {

	public static void main(String[] args) {
		//定义CountDownLatch，需要被到计时2次
		final CountDownLatch countDown = new CountDownLatch(2);

		//定义一个老师T1
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("T1—监考老师发下去试卷" + "等待学生交卷...");
					countDown.await();
					System.out.println("T1—监考老师在继续搞事情，坐在讲台旁边玩着手机等待着学生答题...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"t1");

		//定义第一学生T2
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("T2—有一个学生开始提前交了试卷...");
					Thread.sleep(3000);
					System.out.println("T2-第一个学生交完卷了，T1-老师线程继续等待其他学生交卷...");
					countDown.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		//定义最后一个学生T3
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("T3—最后一个学生开始交试卷...");
					Thread.sleep(4000);
					System.out.println("T3-最后一个学生交完卷了，T1-老师线程继续等待其他学生交卷...");
					countDown.countDown();
					System.out.println("T1老师发现所有学生已经交卷，老师开始整理试卷，贴封条，下班，陪老婆孩子去了");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		t3.start();

	}
}
