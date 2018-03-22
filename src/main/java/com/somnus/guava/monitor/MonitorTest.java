package com.somnus.guava.monitor;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;

import java.util.List;

/**
 * Guava并发：使用Monitor控制并发
 * <p>
 * Monitor就像java原生的synchronized,ReentrantLock一样，每次只允许一个线程执行代码块，且可重占用，每一次占用要对应一次退出占用。
 * <p>
 * Created by lyl on 2018/3/22 0022.
 */
public class MonitorTest {
    private List<String> list = Lists.newArrayList();
    private static final int MAX_SIZE = 10;
    private Monitor monitor = new Monitor();

    //通过Monitor的Guard进行条件阻塞。Monitor.Guard是造成线程阻塞的条件，与Monitor配合使用。
    private Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return list.size() < MAX_SIZE;
        }
    };


    public void addToList(String item) throws InterruptedException {
        monitor.enterWhen(listBelowCapacity); //Guard(形如Condition)，不满足则阻塞，而且我们并没有在Guard进行任何通知操作
        try {
            list.add(item);
        } finally {
            monitor.leave();
        }

    }


//    就如上面，我们通过if条件来判断是否可进入Monitor代码块，并再try/finally中释放：
//            if(monitor.enterIf(guardCondition)) {
//        try {
//            doWork();
//        }finally {
//            monitor.leave();
//        }
//    }


/**
 * 其他的Monitor访问方法：
 * Monitor.enter //进入Monitor块，将阻塞其他线程直到Monitor.leave
 * Monitor.tryEnter//尝试进入Monitor块，true表示可以进入, false表示不能，并且不会一直阻塞
 * Monitor.tryEnterIf//根据条件尝试进入Monitor块
 * 这几个方法都有对应的超时设置版本。
 */

}
