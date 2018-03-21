package com.somnus.thread.blockingQueue;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * * 功能说明：管理任务
 * Created by lyl on 2017/7/15 0015.
 */
public class TaskQueueDaemonThread {

    private static final Logger LOG = Logger.getLogger(TaskQueueDaemonThread.class);

    private TaskQueueDaemonThread() {
    }

    private static class LazyHolder {
        private static TaskQueueDaemonThread taskQueueDaemonThread = new TaskQueueDaemonThread();
    }

    public static TaskQueueDaemonThread getInstance() {
        return LazyHolder.taskQueueDaemonThread;
    }

    Executor executor = Executors.newFixedThreadPool(9);

    /**
     * 初始化守护线程
     */
    public void init() {
        /*守护线程*/
        Thread daemonThread = new Thread(this::execute);
        daemonThread.setDaemon(true);
        daemonThread.setName("Task Queue Daemon Thread");
        daemonThread.start();
    }

    //执行任务（消费者）
    private void execute() {
        while (true) {
            try {
                //从延迟队列中取值,如果没有对象队列一直阻塞等待
                Runnable r1 = queue.take();
                if (r1 == null) {
                    continue;
                }
                //为线程池添加任务
                executor.execute(r1);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    //创建一个最初为空的新 BlockingQueue ，里面能够存放1000个数据
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1000);
    // private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    //不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE
    // private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);

    /**
     * 添加任务（等于生产者）
     */
    public void put(Runnable r1) {
        try {
            System.out.println("生产线程：" + r1);
            queue.put(r1);
            //将任务放在延迟的队列中，如果队列是满的话，会阻塞当前线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束任务
     *
     * @param r1
     */
    public boolean endTask(Runnable r1) {
        return queue.remove(r1);
    }
}