package com.somnus.thread.threadPool;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 带返回值的线程，可以捕获到子类抛出的异常
 *
 * @author lyl
 * @version 2020/8/19 0019 14:50:42
 */
public class TestMain2 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = ViThreadPoolManager.getInstance().getThreadPool().getThreadPoolExecutor();
        Future<String> future = MoreExecutors.listeningDecorator(threadPoolExecutor).submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("do something 1");
                exceptionMethod();
                System.out.println("do something 2");
                return "test result";
            }
            private void exceptionMethod() {
                throw new RuntimeException("ChildThread1 exception");
            }
        });
        try {
            String mess = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(String.format("handle exception in child thread. %s", e));
        }

    }
}
