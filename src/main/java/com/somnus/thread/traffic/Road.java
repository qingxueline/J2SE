package com.somnus.thread.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 每个Road对象代表一条路线，总共有12条路线， 即系统中总共要产生12个Road实例对象。 每条路线上随机增加新的车辆，增加到一个集合中保存。
 * 每条路线每隔一秒都会检查控制本路线的灯是否为绿， 是则将本路线保存车的集合中的第一辆车移除，即表示车穿过了路口。
 */
public class Road {
    private List<String> vechicles = new ArrayList<String>();// 装入汽车对象

    private String name = null;

    public Road(String name) {
        // 把每条路线都作为一个线程来执行
        this.name = name;

        // 模拟车辆不断随机上路的过程
        ExecutorService pool = Executors.newSingleThreadExecutor();// 建立一个线程池，产生一个ExecutorService对象，这个对象只有一个线程可用来执行任务，若任务多于一个，任务将按先后顺序执行。
        pool.execute(new Runnable() {
            public void run() {
                for (int i = 1; i < 1000; i++) {
                    try {
                        Thread.sleep((new Random().nextInt(10) + 1) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    vechicles.add(Road.this.name + "_" + i);// 内部类访问成员变量，用全称，用括号中的字符串表示一辆车的名字
                }
            }

        });

        // 每隔一秒检查对应的灯是否为绿，是则放行一辆车
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);// 建立一个线程调度对象,参数1表示池中保持一个线程，产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，若任务数量大于poolSize，任务会在一个queue里等待执行
        // 在只有一个线程的时候，他和newSingleThreadExecutor()作用是一样的。
        timer.scheduleAtFixedRate(new Runnable() {
                                      public void run() {
                                          if (vechicles.size() > 0) {
                                              boolean lighted = Lamp.valueOf(Road.this.name).isLighted();// 检查绿灯是否亮，内部类访问外部类的非Final型的变量name，要加全称
                                              if (lighted) {
                                                  System.out.println(vechicles.remove(0) + " is traversing !");// 在集合中删除最先加入的元素，表示车辆已经通过
                                              }
                                          }

                                      }
                                  },
                1,// 过1单位时间执行run()
                1,// 执行完等待1单位时间
                TimeUnit.SECONDS);// 定义时间的单位
    }
}
