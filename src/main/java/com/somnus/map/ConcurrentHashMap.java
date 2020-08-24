package com.somnus.map;

import java.util.HashMap;
import java.util.Map;

/**
 * ConcurrentHashMap和HashTable对比
 * ConcurrentHashMap采用了更细粒度的锁来提高在并发情况下的效率。
 * ConcurrentHashMap将Hash表默认分为16个桶（每一个桶可以被看作是一个Hashtable），大部分操作都没有用到锁，
 * 而对应的put、remove等操作也只需要锁住当前线程需要用到的桶，而不需要锁住整个数据。采用这种设计方式以后，在大并发的情况下，同时可以有16个线程来访问数据。
 * 显然，大大提高了并发性。
 * <br/>
 * 只有个别方法（例如size()方法和containsValue()方法）可能需要锁定整个表而不是某个桶，在实现的时候，需要按照顺序锁定所有桶，
 * 操作完毕后，又“按顺序”释放所有桶，“按顺序”的好处是能防止死锁的发生。
 * 参考：https://blog.csdn.net/weixin_39651041/article/details/79953811
 *
 * Synchronized容器和Concurrent容器有什么区别？
 *
 * 在Java语言中，多线程安全的容器主要分为两种：Synchronized和Concurrent，虽然它们都是线程安全的，但是它们在性能方面差距比较大。
 *
 * Synchronized容器（同步容器）主要通过synchronized关键字来实现线程安全，在使用的时候会对所有的数据加锁。需要注意的是，由于同步容器将所有对容器状态的访问都串行化了，
 * 这样虽然保证了线程的安全性，但是这种方法的代价就是严重降低了并发性，当多个线程竞争容器时，吞吐量会严重降低。于是引入了Concurrent容器（并发容器）
 * ，Concurrent容器采用了更加智能的方案，该方案不是对整个数据加锁，而是采取了更加细粒度的锁机制，因此，在大并发量的情况下，拥有更高的效率。
 */
public class ConcurrentHashMap {
	public static void main(String[] args) throws InterruptedException {

		final java.util.concurrent.ConcurrentHashMap<Integer,Integer> map = new java.util.concurrent.ConcurrentHashMap<Integer,Integer>();
		// 同时启动10个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	for (int j = 0; j < 100; j++) {
                        map.put(j, j);
                    }
                	System.out.println(Thread.currentThread().getName()+" put over");
                }
            }).start();
        }

        Thread.sleep(1000);

        System.out.println(Thread.currentThread().getName()+"i = " + map.size());

        // 同时启动10个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	for (int j = 0; j < 100; j++) {
                        map.get(j);
                    }
                	System.out.println(Thread.currentThread().getName()+" get over");
                }
            }).start();
        }
	
	}
}
