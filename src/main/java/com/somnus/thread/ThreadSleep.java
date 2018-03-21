package com.somnus.thread;

/** 
 * 这里启动了10W个线程，每个线程睡眠1秒，但用不了10W秒，可能就几秒钟就执行完
 * 在开启的线程不算太多（比如不到万级）每个线程基本都会立即得到执行，而且得到执行的概率都是基本差不多
 * 比如这里是10W个线程，但由于CPU的高性能，可能一下子就可以执行2W个线程，那么实际上这里总共也才睡眠5s
 * @author Somnus
 * date 2015年5月8日 下午3:50:48  
 */
public class ThreadSleep {
    public static void main(String[] args) {
        // 同时启动100000个线程
        for (int i = 0; i < 100000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("********"+Thread.currentThread().getName() +"********");
                }
            }).start();
        }
    }
}
