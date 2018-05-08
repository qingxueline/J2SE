package com.somnus.queue.delayQueue;

/**
 * Created by lyl on 2017/7/14 0014.
 */
public class DelayQueueTest {

    private static Long ACTIVE_TIME = 1000*10L;//延迟执行时间，6秒后执行

    public static void main(String[] args) {
        TaskQueueDaemonThread.getInstance().init();
        for (Long i = 0L; i <= 1000000000L; i++) {
            System.out.println("产生问题对象：" + i);
            TaskQueueDaemonThread.getInstance().put(ACTIVE_TIME, new Order(String.valueOf(i)));
        }
    }
}


class Order implements Runnable {

    private String i;

    public Order() {
    }

    public Order(String i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("..................已处理第 " + i + " 个问题对象");
    }


}