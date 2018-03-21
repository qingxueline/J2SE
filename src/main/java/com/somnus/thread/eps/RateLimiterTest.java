package com.somnus.thread.eps;

import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
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

        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start) / 1000 + "秒");
    }
}