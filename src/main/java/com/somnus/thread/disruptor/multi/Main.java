package com.somnus.thread.disruptor.multi;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

//多生产者、消费者使用
public class Main {
	
	public static void main(String[] args) throws Exception {

		//创建RingBuffer
		RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, //生产类型，MULTI表示可以多个生产者
						new EventFactory<Order>() {  
				            @Override  
				            public Order newInstance() {  
				                return new Order();  
				            }  
				        }, 
				        1024 * 1024, 
						new YieldingWaitStrategy());

		//构建消费者与缓存RingBuffer之间的桥梁。消费者并不直接访问RingBuffer，从而能减少RingBuffer上的并发冲突。
		SequenceBarrier barriers = ringBuffer.newBarrier();

		//创建多个消费者
		Consumer[] consumers = new Consumer[3];
		for(int i = 0; i < consumers.length; i++){
			consumers[i] = new Consumer("c" + i);
		}
		
		WorkerPool<Order> workerPool =
				new WorkerPool<Order>(ringBuffer,
						barriers,
						new IntEventExceptionHandler(),
						consumers);

		//将消费进度仍给生产者监控，用来平衡生产消费
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
		ExecutorService executor =Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		//执行消费
        workerPool.start(executor);
        final CountDownLatch latch = new CountDownLatch(1);

        //创建生产者
        for (int i = 0; i < 100; i++) {  
        	final Producer p = new Producer(ringBuffer);
        	new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//使当前线程等待,直到锁向下计数为零，除非线程 interrupted。
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int j = 0; j < 100; j ++){
						p.onData(UUID.randomUUID().toString());
					}
				}
			}).start();
        }

        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
        Thread.sleep(5000);
        System.out.println("总数:" + consumers[0].getCount() );
	}

	//异常处理器
	static class IntEventExceptionHandler implements ExceptionHandler {  
	    @Override
		public void handleEventException(Throwable ex, long sequence, Object event) {}
	    @Override
		public void handleOnStartException(Throwable ex) {}
	    @Override
		public void handleOnShutdownException(Throwable ex) {}
	} 
}
