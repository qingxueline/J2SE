package com.somnus.map;

/**
 * Hashtable通过使用synchronized修饰方法的方式来实现多线程同步，因此，Hashtable的同步会锁住整个数组
 */
public class HashTable {
	
	public static void main(String[] args) {

		final java.util.Hashtable<Integer,Integer> map = new java.util.Hashtable<Integer,Integer>();
		// 同时启动10个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	for (int j = 0; j < 100000; j++) {
                        map.put(j, j);
                    }
                	System.out.println(Thread.currentThread().getName()+" put over");
                }
            }).start();
        }
        // 同时启动10个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	for (int j = 0; j < 100000; j++) {
                        map.get(j);
                    }
                	System.out.println(Thread.currentThread().getName()+" get over");
                }
            }).start();
        }
	
	}
	
}
