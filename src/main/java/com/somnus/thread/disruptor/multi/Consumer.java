package com.somnus.thread.disruptor.multi;

import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.WorkHandler;

//消费者，WorkHandler接口来自框架
public class Consumer implements WorkHandler<Order>{
	
	private String consumerId;

	//计算器
	private static AtomicInteger count = new AtomicInteger(0);
	
	public Consumer(String consumerId){
		this.consumerId = consumerId;
	}

	@Override
	public void onEvent(Order order) throws Exception {

		//这里是消费逻辑，onEvent()方法回进行回到，数据读取全部已经由框架负责封装。
		System.out.println("当前消费者: " + this.consumerId + "，消费信息：" + order.getId());
		count.incrementAndGet();
	}
	
	public int getCount(){
		return count.get();
	}

}
