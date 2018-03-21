package com.somnus.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest12EggSyn{
	public static void main(String args[]){
		final Plate plate = new Plate();
		for (int i = 0; i < 10; i++){
			new Thread(new Runnable(){
				private Object egg = new Object();
				public void run(){
					plate.putEgg(egg);
				}
			}).start();
			
			new Thread(new Runnable(){
				public void run(){
					plate.getEgg();
				}
			}).start();
		}
	}
}
/**
 * 假设有这样一种情况，有一个盘子，盘子里只能放一个鸡蛋，A线程专门往盘子里放鸡蛋，
 * 如果盘子里有鸡蛋，则一直等到盘子里没鸡蛋，B线程专门从盘子里取鸡蛋，
 * 如果盘子里没鸡蛋，则一直等到盘子里有鸡蛋。这里盘子是一个互斥区，
 * 每次放鸡蛋是互斥的，每次取鸡蛋也是互斥的，A线程放鸡蛋，
 * 如果这时B线程要取鸡蛋，由于A没有释放锁，B线程处于等待状态，进入阻塞队列，放鸡蛋之后，
 * 要通知B线程取鸡蛋，B线程进入就绪队列，反过来，B线程取鸡蛋，
 * 如果A线程要放鸡蛋，由于B线程没有释放锁，A线程处于等待状态，进入阻塞队列，取鸡蛋之后，
 * 要通知A线程放鸡蛋，A线程进入就绪队列。我们希望当盘子里有鸡蛋时，A线程阻塞，B线程就绪，
 * 盘子里没鸡蛋时，A线程就绪，B线程阻塞
 */
class Plate{
	/** 装鸡蛋的盘子 */
	List<Object> eggs = new ArrayList<Object>();
	
	/** 放鸡蛋 */
	public synchronized void putEgg(Object egg){
		while (eggs.size() == 1){
			try{
				wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		eggs.add(egg);// 往盘子里放鸡蛋
		notify();// 唤醒阻塞队列的某线程到就绪队列
		System.out.println("放入鸡蛋");
	}

	/** 取鸡蛋 */
	public synchronized Object getEgg(){
		while (eggs.size() == 0){
			try{
				wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		Object egg = eggs.get(0);
		eggs.clear();// 清空盘子
		notify();// 唤醒阻塞队列的某线程到就绪队列
		System.out.println("拿到鸡蛋");
		return egg;
	}
}
