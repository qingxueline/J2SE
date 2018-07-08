package com.somnus.thread;

/**
 * 守护线程。守护线程的作用就是系统的守护者，在后台默默得完成系统性的任务，比如垃圾回收。与它对应的是用户线程，用户线程可以任务是系统的工作线程，它会
 * 完成这个程序应该要完成的业务操作。如果用户线程全部结束，也就意味着整个系统已经没有时间可做了，守护线程要守护的对象也就不存在了，所以守护线程也自然要结束
 * 然后程序停止。在java内，当线程只剩下守护线程时，java虚拟机自然会退出。
 */
public class DaemonTest {

    public static class DaemonT extends Thread{
        @Override
        public void run() {
           while (true){
               System.out.println("I am alive");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    public static void main(String[] args)throws InterruptedException{
        Thread t1= new DaemonT();

        //设置为守护线程。如果不设置会true，它就是用户线程。
        t1.setDaemon(true);
        t1.start();

        //如果不把T1设置为守护线程，main用户线程结束后，T1线程还会不断的打印，永远不会停止。
        Thread.sleep(2000);
    }
}
