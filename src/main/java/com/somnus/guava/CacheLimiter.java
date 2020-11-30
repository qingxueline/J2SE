package com.somnus.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用Cache来存储计数器，实现限流
 *
 * @author lyl
 * @version 2020/9/23 0023 15:58:26
 */
public class CacheLimiter {

    public void add() {
        //使用Cache来存储计数器，过期时间设置为2秒。
        LoadingCache<Long, AtomicLong> counter = CacheBuilder
                .newBuilder()
                //设置时间对象没有被写访问则，对象2秒从内存中删除
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, AtomicLong>() {
                    @Override
                    public AtomicLong load(Long aLong) throws Exception {
                        AtomicLong atomicLong = new AtomicLong(0);
                        System.out.println("---------------------------" + atomicLong.incrementAndGet());
                        return atomicLong;
                    }
                });

        //2秒内限制1000次访问
        long limit = 1000;

        while (true) {
            long currentSeconds = System.currentTimeMillis() / 1000;
            try {
                //当没有缓存时，执行load(Long aLong)方法
                AtomicLong atomicLong = counter.get(currentSeconds);
                System.out.println("===============atomicLong：" + atomicLong);
                Thread.sleep(500);
                if (atomicLong.incrementAndGet() > limit) {
                    System.out.println("==============限制流了");
                    break;
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                break;
            }


            //业务处理
        }
    }

    public static void main(String[] args) {
        new CacheLimiter().add();
    }
}
