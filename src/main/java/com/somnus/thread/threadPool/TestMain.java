package com.somnus.thread.threadPool;

/**
 * @author lyl
 * @version 2020/8/19 0019 14:50:42
 */
public class TestMain {
    public static void main(String[] args) {
        ViThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("xxxxxxxxxxxxxxxxxx");
            }
        });
    }
}
