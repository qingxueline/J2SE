package com.somnus.thread;
/**
 * wait与notify方法都是定义在Object类中，而且是final的，因此会被所有的Java类所继承并且无法重写。
 * 这两个方法要求在调用时线程应该已经获得了对象的锁，因此对这两个方法的调用需要放在synchronized方法或块当中。
 * 当线程执行了wait方法时，它会释放掉对象的锁。
 * 
 * 另一个会导致线程暂停的方法就是Thread类的sleep方法，它会导致线程睡眠指定的毫秒数，
 * 但线程在睡眠的过程中是不会释放掉对象的锁的。
 *
 */
public class ThreadTest15{
	public static void main(String[] args){
		final Math math = new Math();
		for(int i=0;i<3;i++){
			new Thread(new Runnable(){
				public void run(){
					for(int i=0;i<10;i++){
						math.increase();
						System.out.println(Thread.currentThread().getName()+":increase");
					}
				}
			}).start();
			
			new Thread(new Runnable(){
				public void run(){
					for(int i=0;i<10;i++){
						math.decrease();
						System.out.println(Thread.currentThread().getName()+":decrease");
					}
				}
			}).start();
		}
	}
}

class Math{
	private int num;

	public synchronized void increase(){
		if(num != 0){//while(num != 0)
			try{
				wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		num++;
		System.out.println("[" + num+"]");
		notify();
	}
	public synchronized void decrease(){
		if(num == 0){//while(num == 0)
			try{
				wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		num--;
		System.out.println("{" + num+"}");
		notify();
	}
	
}