package com.somnus.thread.disruptor.base;

/**
 * 生产对象
 * @author lyl
 */ //http://ifeve.com/disruptor-getting-started/
public class LongEvent {

    private long value;
    public long getValue() { 
        return value; 
    } 
 
    public void setValue(long value) { 
        this.value = value; 
    } 
} 