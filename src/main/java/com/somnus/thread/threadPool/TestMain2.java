package com.somnus.thread.threadPool;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.util.concurrent.*;

/**
 * 带返回值的线程，可以实现异步返回结果，可以捕获到子类抛出的异常
 * <p>
 * 举个例子：在泡茶的时候，需要先烧水，准备茶叶，然后在泡一会。这样就使用Future返回的结果，传递给下一个线程使用。
 *
 * @author lyl
 * @version 2020/8/19 0019 14:50:42
 */
public class TestMain2 {
    public static void main(String[] args) throws InterruptedException {
        String name = "张三";
        ThreadPoolExecutor threadPoolExecutor = ViThreadPoolManager.getInstance().getThreadPool().getThreadPoolExecutor();
        ListenableFuture<String> listenableFuture = MoreExecutors.listeningDecorator(threadPoolExecutor).submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("进入烧水线程...........");
                System.out.println(name + "打开水龙头");
//                exceptionMethod();
                System.out.println(name + "开始接水");
                System.out.println("开始烧水");
                Thread.sleep(4000);
                return "水烧开了";
            }

            //停电，默认异常
            private void exceptionMethod() {
                throw new RuntimeException("惨了，今天断电停水了");
            }
        });
        System.out.println("主线程继续执行...............");

        //默认取消子线程执行
        int ms = 0;
        while (!listenableFuture.isDone()) {
            System.out.println("开始还没烧好，烧水程序烧水中，已经烧了" + ms + "秒");
            Thread.sleep(1000);
            ms++;
            if (ms == 5) {
                //烧了3秒，突然有事，不想泡茶了。取消子线（烧水）程任务执行
                listenableFuture.cancel(true);
            }
        }

        //Futures提供回调函数
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                if ("水烧开了".equals(result)) {
                    System.out.println(name + "放入茶叶，开始泡茶了");
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                System.out.printf("onFailure %s%n", throwable.getMessage());
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