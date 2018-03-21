package com.somnus.thread.threadlocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** 
 * @Title: MyThreadLocal.java 
 * @Package com.somnus.thread.threadlocal 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月3日 下午2:41:22 
 * @version V1.0 
 */
public class MyThreadLocal<T> {
 
    private Map<Thread, T> container = Collections.synchronizedMap(new HashMap<Thread, T>());
 
    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }
 
    public T get() {
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if (value == null && !container.containsKey(thread)) {
            value = initialValue();
            container.put(thread, value);
        }
        return value;
    }
 
    public void remove() {
        container.remove(Thread.currentThread());
    }
    /**
     * 为什么 initialValue() 方法是 protected 的呢？
     * 就是为了提醒程序员们，这个方法是要你们来实现的，请给这个线程局部变量一个初始值吧。
     */
    protected T initialValue() {
        return null;
    }
}
