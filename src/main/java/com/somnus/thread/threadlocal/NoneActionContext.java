/**
 * 
 */
package com.somnus.thread.threadlocal;

/**
 * 这个例子没有用ThreadLocal
 * 假设现在开始启动线程
 * 线程一开始启动-->但在3中只累加到了500就失去cpu的调度
 * 这时候线程二开始启动--> 1 、2、3、4 分别执行完-->打印结果 Thread-2 : 1000
 * 紧接着开始线程一没有执行完的，继续累加，但是这里index引用所指向的对象已经被改变，不再是原来的那个累加到了500的Index，而是线程二累加到1000的Index
 * 线程一把剩下的执行完，打印的结果：  Thread-1 : 1500，也就好解释了
 * 为什么会有ThreadLocal现在也好理解，此例子中每个产生的Index对象没有和当前线程绑定，而ThreadLocal刚好就能把每个产生的对象绑定到当前线程
 * 
 * Struts2中的ActionContext类就是这个道理，每次请求都会创建一个ActionContext上下文对象，
 * 所有的当前request、session、application都会放入此对象
 * 但是我们可以发现每次我们都可以通过ActionContext.getContext(),都能拿到当前请求线程下的ActionContext上下文对象
 * 试想如果没有这样做，那我们明明是在处理用户A的请求，要拿到他的username，却拿到的是用户B的username
 * 这里的Index其实和ActionContext就是同样的道理
 */
public class NoneActionContext {
	private Index local;

	public Index getIndex() {
		return local;
	}
	public void setIndex(Index index){
		this.local = index;
	}
	private static NoneActionContext context ;
	
	private NoneActionContext(){}
	
	public static NoneActionContext getInstance(){
		if(context == null){
			context = new NoneActionContext();
		}
		return context;
	}

	public static void main(String[] args) {
		final NoneActionContext context = NoneActionContext.getInstance();
		for (int j = 0; j < 10; j++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					context.setIndex(new Index());		//1
					// 获取当前线程的本地变量，然后累加1000次
					Index index = context.getIndex();	//2
					for (int i = 0; i < 1000; i++) {
						index.increase();				//3
					}
					// 重新设置累加后的本地变量
					context.setIndex(index);			//4
					System.out.println(Thread.currentThread().getName() + " : " + index.num);
				}
			}, "Thread-" + j).start();
		}
	}
}
