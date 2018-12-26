package com.somnus.thread.disruptor.base;

import com.lmax.disruptor.EventHandler;

/**
 * 消费事件处理器，负责消费事件（即消费者）
 * @author lyl
 */
public class LongEventHandler implements EventHandler<LongEvent>  {
	/**
	 * 消费事件，负责消费
	 * @param longEvent 。
	 * @param l 。
	 * @param b 。
	 */
	@Override
	public void onEvent(LongEvent longEvent, long l, boolean b) {
		//消费逻辑
		System.out.println(longEvent.getValue()); 		
	}

}
