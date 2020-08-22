package com.somnus.guava;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import com.somnus.thread.threadPool.ViThreadPoolManager;
import lombok.SneakyThrows;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lyl
 * @version 2020/8/22 0022 00:41:19
 */
public class FuturesTest {
    public static void main(String[] args) {
        should_test_furture();
    }
    public static void should_test_furture() {
        ThreadPoolExecutor threadPoolExecutor = ViThreadPoolManager.getInstance().getThreadPool().getThreadPoolExecutor();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPoolExecutor);
        ListenableFuture<Integer> future1 = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws InterruptedException {
                Thread.sleep(1000);
                System.out.println("call future 1.");
                return 1;
            }
        });

        ListenableFuture<Integer> future2 = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws InterruptedException {
                Thread.sleep(1000);
                System.out.println("call future 2.");
                return 2;
            }
        });
        //对多个ListenableFuture的合并，返回一个当所有Future成功时返回多个Future返回值组成的List对象。注：当其中一个Future失败或者取消的时候，将会进入失败或者取消。
        final ListenableFuture allFutures = Futures.allAsList(future1, future2);

        //对于ListenableFuture的返回值进行转换。
        ListenableFuture transform = Futures.transformAsync(allFutures, new AsyncFunction<List<Integer>, Map<Integer, String>>() {
            @Override
            public ListenableFuture<Map<Integer, String>> apply(List<Integer> results) {
                Map<Integer, String> map = Maps.newHashMap();
                results.forEach(index -> {
                    if (index == 1) {
                        map.put(index, "一号子程序");
                    }
                    if (index == 2) {
                        map.put(index, "二号子程序");
                    }
                });
                //immediateFuture 表示该方法立即返回
                return Futures.immediateFuture(map);
            }
        });

        //Futures提供回调函数
        Futures.addCallback(transform, new FutureCallback<Object>() {
            @SneakyThrows
            @Override
            public void onSuccess(Object result) {
                System.out.println(result.getClass());
                System.out.printf("sonSuccess: %s%n", result.toString());
            }

            @Override
            public void onFailure(Throwable thrown) {
                System.out.printf("onFailure：%s%n", thrown.getMessage());
            }
        });
        System.out.println("main task done.....");
    }
}

/**
 * 同时Guava中Futures对于Future扩展还有：
 * <p>
 * transform：对于ListenableFuture的返回值进行转换。
 * allAsList：对多个ListenableFuture的合并，返回一个当所有Future成功时返回多个Future返回值组成的List对象。注：当其中一个Future失败或者取消的时候，将会进入失败或者取消。
 * successfulAsList：和allAsList相似，唯一差别是对于失败或取消的Future返回值用null代替。不会进入失败或者取消流程。
 * immediateFuture/immediateCancelledFuture： 立即返回一个待返回值的ListenableFuture。
 * makeChecked: 将ListenableFuture 转换成CheckedFuture。CheckedFuture 是一个ListenableFuture ，其中包含了多个版本的get 方法，方法声明抛出检查异常.这样使得创建一个在执行逻辑中可以抛出异常的Future更加容易
 * JdkFutureAdapters.listenInPoolThread(future): guava同时提供了将JDK Future转换为ListenableFuture的接口函数
 */
