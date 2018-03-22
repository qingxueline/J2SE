package com.somnus.guava.monitor;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;

import java.util.Iterator;
import java.util.List;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class MonitorExample {
    private static final int MAX_SIZE = 10;
    private Monitor monitor = new Monitor();
    private List<String> list = Lists.newArrayList();

    Monitor.Guard listBelowCapacity = new
            Monitor.Guard(monitor) {
                @Override
                public boolean isSatisfied() {
                    return list.size() < MAX_SIZE;
                }
            };

    public void addToListWait(String item) throws InterruptedException {
        // 超过MAX_SIZE， 会锁死（阻塞）
        this.monitor.enterWhen(listBelowCapacity);
        try {
            list.add(item);
            System.out.println("添加元素[" + item + "]成功，当前List.size=" + list.size() + "～");
        } finally { // 确保线程会退出Monitor锁
            monitor.leave();
        }
    }

    public void addToListSkipWait(String item) throws InterruptedException {
        // 超过MAX_SIZE， 会锁死
        //this.monitor.enterWhen(listBelowCapacity);

        // 超过返回false  不会锁死
        Boolean isOK = monitor.tryEnterIf(listBelowCapacity);
        System.out.println("Thread[" + Thread.currentThread().getName() + "] item=" + item + ",获取令牌：isOK=" + isOK);
        if (isOK) {
            try {
                list.add(item);
                System.out.println("添加元素[" + item + "]成功，当前List.size=" + list.size() + "～");
            } finally { // 确保线程会退出Monitor锁
                monitor.leave();
            }
        }
    }

    public static void main(String[] args) {
        final MonitorExample monitorDemo = new MonitorExample();
        for (int i = 0; i < 5; i++) {
            new Thread() {
                public void run() {
                    for (int count = 0; count < 6; count++) {
                        try {
                            // monitorDemo.addToListWait(count + "------------------>" + Thread.currentThread().getName());
                            monitorDemo.addToListSkipWait(count + "------------------>" + Thread.currentThread().getName());
                            Thread.sleep(100L);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }.start();
        }

        // 等待所有线程执行完毕
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------- 长长的分割线 ---------------------------");
        Iterator iteratorStringList = monitorDemo.list.iterator();
        while (iteratorStringList.hasNext()) {
            System.out.println(iteratorStringList.next());
        }
    }

}
