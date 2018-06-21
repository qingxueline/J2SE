package com.somnus.thread.multi_001.sync005;
/**
 * synchronized异常
 * @author alienware
 *
 */
public class SyncException {

	private int i = 0;
	public synchronized void operation(){
		while(true){
			try {
				i++;
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName() + " , i = " + i);
				if(i == 20){
					Integer.parseInt("a");

				}
			} catch (InterruptedException e) {
				/**
				 * synchronized中遇到异常的处理。如果20条数据是一个整体，数据必须是整体全部都整个的，那么就抛出RuntimeException(),事务就会回滚。
				 * 如果数据不是一个整天，20条数据运行部分成功，部分失败，那家就可以打印日志记录。失败的过后处理。
				 */
				throw new RuntimeException();
//				continue;
			}
		}
	}
	
	public static void main(String[] args) {
		
		final SyncException se = new SyncException();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				se.operation();
			}
		},"t1");
		t1.start();
	}
	
	
}
