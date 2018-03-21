package com.somnus.thread;

/**
 * Created by  on 2017/5/11 0011.
 */

import com.somnus.http.HttpUtils;
import com.somnus.http.HttpXMLUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CountdownLatchTest {
    private static String getTradeNo() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        //0 代表前面填充0
        //14 代表长度为14
        //d 代表参数为正数
        return df.format(date) + String.format("%014d", hashCodeV);
    }

    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(5);
        ExecutorService service = Executors.newCachedThreadPool(); //创建一个线程池
        final CountDownLatch cdOrder = new CountDownLatch(1);//下达任务
        final CountDownLatch cdAnswer = new CountDownLatch(50);//执行任务的线程
        for (int i = 0; i < 50; i++) {//创建要执行的线程
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() + "正准备接受命令");
                        cdOrder.await();

                        String notification = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NotifyContractRoot><TYPE>1</TYPE><GROUP_TRANSACTIONID>" + CountdownLatchTest.getTradeNo() + "</GROUP_TRANSACTIONID><CUSTOMER_TRANSACTIONID>null</CUSTOMER_TRANSACTIONID><STATUSINFO>竣工</STATUSINFO><ACCNBR>1064916945256111</ACCNBR><ACCEPTTYPE>1</ACCEPTTYPE><ACCEPTMSG>这是一条测试数据！！！！！！！！！！！！！</ACCEPTMSG><STATUSDT>20161102162505</STATUSDT><RESULTMSG>成功测试数据3333333333333333333</RESULTMSG></NotifyContractRoot>";
//        String requestUrl = "http://183.62.12.17:7080/call/automationrule/notice";

                        String requestUrl = "http://localhost:8080/call/automationrule/notice";
                        String reqStr = HttpXMLUtils.doXmlPost(requestUrl, notification);
                        System.out.println("线程" + Thread.currentThread().getName() + "已接受命令");
                        Thread.sleep((long) (/*Math.random() * */1000 * 10));
                        System.out.println("线程" + Thread.currentThread().getName() + "回应结果：" + reqStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        cdAnswer.countDown(); //任务执行完毕，返回给指挥官，cdAnswer减1。
                    }
                }
            };
            service.execute(runnable);//为线程池添加任务
        }
        try {
            Thread.sleep((long) (/*Math.random() * */1000 * 20));
            System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");
            cdOrder.countDown(); //发送命令，cdOrder减1，处于等待的任务停止等待转去执行任务。
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            cdAnswer.await(); //命令发送后主线程处于等待状态，一旦cdAnswer为0时停止等待继续往下执行
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        service.shutdown(); //任务结束，停止线程池的所有线程
    }
}