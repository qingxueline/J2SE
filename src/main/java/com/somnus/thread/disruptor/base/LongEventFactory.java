package com.somnus.thread.disruptor.base;

import com.lmax.disruptor.EventFactory;

/**
 * 使用事件工厂EventFactory，来实例化Event事件对象。
 * @author lyl
 */
public class LongEventFactory implements EventFactory {

    /**
     * 创造生产的事件（产品）
     * @return 。
     */
    @Override 
    public Object newInstance() { 
        return new LongEvent(); 
    } 
} 