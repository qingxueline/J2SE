package com.somnus.thread;

/**
 * 子线程循环2次，主线程循环5次，如此循环5次
 */
public class ThreadTest13{
	public static void main(String args[]){
		final Business business = new Business();
		new Thread(new Runnable(){
			public void run(){
				for (int i = 0; i < 5; i++){//如此循环5次
					try{
						business.sub(i);
					}
					catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		for (int i = 0; i < 5; i++){//如此循环5次
			try{
				business.main(i);
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

class Business{
	private boolean bool = true;

	public synchronized void main(int loop) throws InterruptedException{
		while (bool){
			wait();
		}
		for (int i = 0; i < 5; i++){//主线程循环5次
			System.out.println("main thread seq of " + i + ", loop of " + loop);
			//打印五次
		}
		bool = true;
		notify();
	}

	public synchronized void sub(int loop) throws InterruptedException{
		while (!bool){
		    //此处用if在主线程、子线程只有一对的情况下没什么区别，
		    //但是在多对的时候，子线程执行完再次唤醒子线程的时候不重新判断的话，子线程继续执行
		    //本来应该由主线程接替，让它继续等待
		    wait();
		}
		for (int i = 0; i < 2; i++){//子线程循环2次
			System.out.println("sub thread seq of " + i + ", loop of " + loop);
			//打印两次
		}
		bool = false;
		notify();
	}

}

