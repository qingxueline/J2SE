package com.somnus.guava;

import com.google.common.base.Stopwatch;
import com.somnus.uuid.Identities;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.guava
 * @date 2018/05/27 11:19
 * @description 利用StopWatch监控Java代码运行时间和分析性能
 * <p>
 * 备注：Spring 也提供有Stopwatch.参考这篇文章：https://blog.csdn.net/weixin_39723544/article/details/102887764?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-9.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-9.nonecase
 */
@Log4j
public class StopwatchTest {

    public static void main(String[] args) {
        process(Identities.get28LenNonce());
    }

    public static void process(String orderNo) {
        try {
            log.info(String.format("Generate an order [%s]", orderNo));
            Stopwatch stopwatch = Stopwatch.createStarted();
            TimeUnit.SECONDS.sleep(1);
            log.info(String.format("====The orderNo [%s] create a time-consuming [%s]", orderNo, stopwatch.stop()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void process() throws InterruptedException {
        // 创建stopwatch并开始计时
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1980);
        // 以秒打印从计时开始至现在的所用时间,向下取整
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 停止计时
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 再次计时
        stopwatch.start();
        Thread.sleep(100);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 2
        // 重置并开始
        stopwatch.reset().start();
        Thread.sleep(1030);
        // 检查是否运行
        System.out.println(stopwatch.isRunning()); // true
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS); // 1031
        System.out.println(millis);
        // 打印
        System.out.println(stopwatch.toString()); // 1.03 s
    }

}
