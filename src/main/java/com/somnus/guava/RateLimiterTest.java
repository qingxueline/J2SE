package com.somnus.guava;

import com.google.common.util.concurrent.RateLimiter;
import com.somnus.thread.eps.UserRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * <p>
 * 并发量：系统同时处理多少个请求。这个是时间没关系，和线程/进程有关系，比如10个线程，并发就是10。另外如果不考虑最终瓶颈，通过增加机器也能提高并发能力。
 * <p/>
 *
 * <p>
 * QPS:   每秒钟处理的请求数。（Requests per second）
 * <p/>
 *
 * <p>
 *   吞吐量：服务端处理的线程数*1000/请求处理时间
 * <p/>
 *
 * <p>
 *     RateLimiter 是Google开源工具包Guava提供了限流工具类。
 *
 * <p/>
 * 限制速率
 * Created by lyl on 2018/3/20 0020.
 */
public class RateLimiterTest {
    public static void main(String[] args) {

        //速率是每秒两个许可
        RateLimiter rateLimiter = RateLimiter.create(10);
        List<Runnable> tasks = new ArrayList<Runnable>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new UserRequest(i));
        }

//        ExecutorService threadPool = Executors.newCachedThreadPool();

//        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start) / 1000 + "秒");
    }
}