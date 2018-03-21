package com.somnus.thread;

public class SynchronizedDemo {
	private boolean ready = false;
	private int result = 0;
	private int number = 1;
	/**
	 * 写操作
	 */
	public void write(){
		ready = true;			//1.1
		number = 2;				//1.2
	}
	/**
	 * 读操作
	 */
	public void read(){
		if(ready){				//2.1
			result = number*3;	//2.2
		}
		System.out.println("result的值为:"+result);
	}
	private class ReadWriteThread extends Thread{
		private boolean flag;
		public ReadWriteThread(boolean flag){
			this.flag = flag;
		}
		@Override
		public void run(){
			if(flag){
				//构造方法中传入true，执行写操作
				write();
			}else{
				//构造方法中传入true，执行读操作
				read();
			}
		}
	}
	public static void main(String[] args) {
		SynchronizedDemo demo = new SynchronizedDemo();
		//启动线程执行写操作
		demo.new ReadWriteThread(true).start();
		//启动线程执行读操作
		demo.new ReadWriteThread(false).start();
	}
}
