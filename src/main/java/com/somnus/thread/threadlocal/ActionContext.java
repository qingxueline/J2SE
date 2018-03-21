/**
 * 
 */
package com.somnus.thread.threadlocal;

/** 
 * @Title: ActionContext.java 
 * @Package com.somnus.thread.threadlocal 
 * @Description: TODO
 * @author Somnus
 * @date 2015年4月25日 上午10:40:32 
 * @version V1.0 
 */
public class ActionContext {
	private static final ThreadLocal<Index> local = new ThreadLocal<Index>();

	public Index getIndex() {
		return local.get();
	}
	public void setIndex(Index index){
		local.set(index);
	}
	private static ActionContext context ;
	
	private ActionContext(){}
	
	public static ActionContext getInstance(){
		if(context == null){
			context = new ActionContext();
		}
		return context;
	}
	
	public void remove(){
		local.remove();
	}
	
	public static void main(String[] args) {
		final ActionContext context = ActionContext.getInstance();
		for (int j = 0; j < 10; j++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					context.setIndex(new Index());
					// 获取当前线程的本地变量，然后累加1000次
					Index index = context.getIndex();
					for (int i = 0; i < 1000; i++) {
						index.increase();
					}
					// 重新设置累加后的本地变量
					context.setIndex(index);
					System.out.println(Thread.currentThread().getName() + " : " + index.num);
					context.remove();
				}
			}, "Thread-" + j).start();
		}
	}

}
