package com.somnus.thread;

/**
 * 
 * @Title: Thread4Test.java 
 * @Package com.somnus.thread 
 * @Description: 多线程环境下，所有的线程共享一个对象，那么这个对象的成员变量，就会被不同的线程所改变，
 * 				Servlet就是这个道理，所以我们不能给其弄出成员变量来，它必须是线程安全的才行
 * @author Somnus
 * @date 2015年4月26日 下午9:43:12 
 * @version V1.0
 */
public class Thread4Test {
    public static void main(String[] args) {

        // 同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.getInstance().inc();
                }
            }).start();
        }
        //如果还有子线程在运行，主线程就让出cpu资源
        //直到所有子线程都运行完了，主线程再继续往下执行
        while(Thread.activeCount()>1){
            Thread.yield();
        }
        // 这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.getInstance().count);
    }
}

class Counter {
    
    public	int count = 0;

    public  void inc() {
        // 这里延迟100毫秒，使得结果明显
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        count++;
    }
    
    private static Counter counter ;
    
    private Counter(){}
	
	public static Counter getInstance(){
		if(counter == null){
			counter = new Counter();
		}
		return counter;
	}
}