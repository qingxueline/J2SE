package com.somnus.thread.threadPool;

/**
 * @author lyl
 * @version 2020/8/19 0019 14:41:10
 */

import com.small.cell.common.thread.base.ThreadPoolManager;

/**
 * 当前类（子系统中定义的类）继承 ThreadPoolManager 类，设置相关参数
 */
public class ViThreadPoolManager extends ThreadPoolManager {
    private static ThreadPoolManager threadPool  = null;

    public synchronized static ThreadPoolManager getInstance() {
        if(threadPool == null) {
            threadPool = new ViThreadPoolManager();
        }
        return threadPool;
    }

    @Override
    protected String getThreadPoolName() {
        return "com.tool.me.vi";
    }

    @Override
    protected int corePoolSize() {
        /**
         * 代码 设置返回值
         */
        return 10;
    }

    @Override
    protected int maximumPoolSize() {
        /**
         * 代码 设置返回值
         */
        return 20;
    }
}