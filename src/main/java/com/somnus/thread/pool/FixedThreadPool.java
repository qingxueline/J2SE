package com.somnus.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

	public static void main(String[] args) {
		/*创建可以容纳3个线程的线程池 */
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i < 5; i++){
            final int taskID = i;
            threadPool.execute(new Runnable(){
                public void run(){
                    for (int i = 1; i < 5; i++){
                        try{
                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");
                    }
                }
            });
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
	}

}
