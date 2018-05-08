package com.somnus.queue.delayQueue;

import org.apache.log4j.Logger;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。DelayQueue是一个没有大小限制的队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。
 * 使用场景：
 * 　　DelayQueue使用场景较少，但都相当巧妙，常见的例子比如使用一个DelayQueue来管理一个超时未响应的连接队列。
 * <p>
 * 使用说明：
 * <br/>
 * 1、在容器初始化的时候已经初始化此类
 * 2、将要执行延迟的业务类，实现一个runnable接口，调用TaskQueueDaemonThread的put方法传入进去.
 * 3、如果需要实现动态的取消任务的话，需要task任务的类重新hashcode方法，最好用业务限制hashcode的冲突发生.
 * <p>
 * 功能说明：管理延迟任务
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

    Executor executor = Executors.newFixedThreadPool(10);

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

    private void execute() {
        while (true) {
            try {
                //从延迟队列中取值,如果没有对象过期则队列一直等待，
                Task<Runnable> t1 = t.take();
                if (t1 != null) {
                    //修改问题的状态
                    Runnable task = t1.getTask();
                    if (task == null) {
                        continue;
                    }
                    //为线程池添加任务
                    executor.execute(task);
                    LOG.info("[at task:" + task + "]   [Time:" + System.currentTimeMillis() + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 创建一个最初为空的新 DelayQueue
     */
    private DelayQueue<Task<Runnable>> t = new DelayQueue<Task<Runnable>>();

    /**
     * 添加任务，
     * time 延迟时间
     * task 任务
     * 用户为问题设置延迟时间
     */
    public void put(long time, Runnable task) {
        //转换成ns
        long nanoTime = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
        //创建一个任务
        Task<Runnable> k = new Task<Runnable>(nanoTime, task);
        //将任务放在延迟的队列中
        t.put(k);
    }

    /**
     * 结束任务
     *
     * @param task
     */
    public boolean endTask(Task<Runnable> task) {
        return t.remove(task);
    }
}