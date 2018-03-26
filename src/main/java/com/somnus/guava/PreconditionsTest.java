package com.somnus.guava;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class PreconditionsTest {

    /**
     * <p>
     * 早在初夏的时候，一个叫刚刚加入我们的同事Bent André向我介绍了用Preconditions进行校验的想法，
     * <br/>但是,直到最近我才发现Google Collections实际上已经包含了相应的实现（我们自己也已经有了相似的实现）。
     * <br/>那么这个实现具体是什么样子的呢？校验就是要求我们准确无误的做一些事情，通常情况下，它看起来就是类似下面这样的代码：
     * <br/>
     * if (count <= 0) {
     * throw new IllegalArgumentException("must be positive: " + count);
     * }
     * <p>
     */
    @Test
    public void checkArgument1Test() {
        int count = 0;
        Preconditions.checkArgument(count > 0, "count 参数必须大于: %s", count);

    }


    @Test
    public void checkArgument2Test() {
        String entry = "1111";
        String aNull = Preconditions.checkNotNull(entry);
        System.out.println(aNull);
    }

 
}
