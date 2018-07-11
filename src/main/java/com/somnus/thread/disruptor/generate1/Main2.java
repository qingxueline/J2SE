package com.somnus.thread.disruptor.generate1;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

//测试使用WorkerPool做消息处理器
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;

        EventFactory<Trade> eventFactory = new EventFactory<Trade>() {
            public Trade newInstance() {
                return new Trade();
            }
        };

        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);

        WorkHandler<Trade> handler = new TradeHandler();

        WorkerPool<Trade> workerPool = new WorkerPool<Trade>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), handler);

        //执行消费
        workerPool.start(executor);

        //下面这个生产8个数据
        for (int i = 0; i < 8; i++) {
            long seq;
            seq = ringBuffer.next();//占个坑 --ringBuffer一个可用区块.一个块放一个Trade
            ringBuffer.get(seq).setId(UUID.randomUUID().toString());
            ringBuffer.get(seq).setName("订单_" + i);
            ringBuffer.get(seq).setPrice(Math.random() * 9999);//给这个区块放入 数据
            ringBuffer.publish(seq);
        }

        Thread.sleep(1000);
        workerPool.halt();
        executor.shutdown();
    }
}  
