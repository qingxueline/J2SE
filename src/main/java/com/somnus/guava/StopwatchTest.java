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
 *
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
}
