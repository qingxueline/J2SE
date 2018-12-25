package com.somnus.thread.disruptor.base;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @author lyl
 */
public class LongEventMain {

	public static void main(String[] args) throws InterruptedException {
		//创建线程工厂
		ThreadFactory threadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
		};

		//创建工厂
		LongEventFactory factory = new LongEventFactory();
		//创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
		int ringBufferSize = 1024 * 1024; // 

		/**
		//BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
		WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		//SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
		WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		//YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
		WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		*/

		/**
		 * 构造参数说明
		 * eventFactory：事件工厂
		 * ringBufferSize:初始化RingBuffer缓存区大小
		 * executor：线程池
		 * producerType：生产模式。支持多生产者-多消费者，单生产者-单消费者等，还支持非常复杂的棱形消费等。
		 * waitStrategy：策略模式
		 */
		//创建disruptor
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, threadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());
		// 消费事件注册，连接消费者
		disruptor.handleEventsWith(new LongEventHandler());
		// 启动
		disruptor.start();
		
		//Disruptor 的事件发布过程是一个两阶段提交的过程：
		//发布事件
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();


		//生产者，将来数据写入到RingBuffer
		LongEventProducer producer = new LongEventProducer(ringBuffer); 
		//LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		for(long l = 0; l<100; l++){
			byteBuffer.putLong(0, l);
			producer.onData(byteBuffer);
//			Thread.sleep(1000);
		}

		disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；disruptor关闭后，RingBuffer里面的数据才会被GC回收。
	}
}
