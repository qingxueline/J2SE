package com.somnus.queue.blockingQueue;

/**
 * 消费者，这里在实际应用在写任务的逻辑
 * Created by lyl on 2017/7/19 0019.
 */
public class Consumer implements Runnable {

    private int i;

    public Consumer(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " >> 消费线程：" + i);
    }
}