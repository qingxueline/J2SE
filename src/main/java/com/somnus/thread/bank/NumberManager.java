package com.somnus.thread.bank;

import java.util.ArrayList;
import java.util.List;

/**
 * 号码机
 */
public class NumberManager {
    private int lastNumber = 0;
    private List<Integer> queueNumbers = new ArrayList<Integer>();

    /**
     * synchronized堵塞的方法，产生号码，客户触发
     *
     * @return
     */
    public synchronized Integer generateNewNumber() {
        queueNumbers.add(++lastNumber);
        return lastNumber;
    }

    /**
     * synchronized堵塞的方法，取走号码，窗口触发
     *
     * @return
     */
    public synchronized Integer fetchNumber() {
        if (queueNumbers.size() > 0) {
            return (Integer) queueNumbers.remove(0);
        } else {
            return null;
        }
    }
}
