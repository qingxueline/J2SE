package com.somnus.queue.blockingQueue;


/**
 * Created by lyl on 2017/7/19 0019.
 */
public class Test {

    public static void main(String[] args) {
        TaskQueueDaemonThread.getInstance().init();
        for (int i = 0; i < 100000000; i++) {TaskQueueDaemonThread.getInstance().put(new Consumer(i));
        }
    }

}
