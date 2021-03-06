package com.somnus.thread.disruptor.base;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;
/**
 * 很明显的是：当用一个简单队列来发布事件的时候会牵涉更多的细节，这是因为事件对象还需要预先创建。
 * 发布事件最少需要两步：获取下一个事件槽并发布事件（发布事件的时候要使用try/finnally保证事件一定会被发布）。
 * 如果我们使用RingBuffer.next()获取一个事件槽，那么一定要发布对应的事件。
 * 如果不能发布事件，那么就会引起Disruptor状态的混乱。
 * 尤其是在多个事件生产者的情况下会导致事件消费者失速，从而不得不重启应用才能会恢复。
 */
public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
		this.ringBuffer = ringBuffer;
	}

	/**
	 *
	 * onData用来发布事件，每调用一次就发布一次事件
	 * 它的参数会用过事件传递给消费者
	 * @param bb 字节缓存区
	 */
	public void onData(ByteBuffer bb){
		//1.获取序号。可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件序号槽
		long sequence = ringBuffer.next();
		try {
			//2.根据序号，取得对象，然后将新的值覆盖到旧对象上。这样原来对象在Disruptor没有被销毁前，对象就不会被销毁，减少了大量的GC处理。
			LongEvent event = ringBuffer.get(sequence);
			//3.从字节缓存区获取数据（用户提供），然后覆盖到生产对象上，最后等于是覆盖了RingBuffer的sequence序号上的旧数据
			event.setValue(bb.getLong(0));
		} finally {
			//4.发布事件
			//注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
			ringBuffer.publish(sequence);
		}
	}
	
	
	
	
	
}
