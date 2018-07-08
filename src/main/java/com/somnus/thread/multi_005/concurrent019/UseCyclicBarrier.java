package com.somnus.thread.multi_005.concurrent019;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseCyclicBarrier {

    private final static int PARTIES = 3;

    static class Runner implements Runnable {
        private CyclicBarrier barrier;
        private String name;

        public Runner(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000 * (new Random()).nextInt(5));
                System.out.println(name + " 准备OK.");

                //此段代码会直接等待，直到达到PARTIES设定的值, barrier.await()后面的代码才会被执行。
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + " Go!!");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        //定义CyclicBarrier
        CyclicBarrier barrier = new CyclicBarrier(PARTIES);

        //定义三个线程
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //定义三个运动员
        executor.submit(new Thread(new Runner(barrier, "吕布")));
        executor.submit(new Thread(new Runner(barrier, "赵云")));
        executor.submit(new Thread(new Runner(barrier, "马超")));

        executor.shutdown();
    }

}  