package com.somnus.guava;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.Map;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class SplitterTest {
    @Test
    public void splitterTest() {

        //使用 Splitter#split 完成拆分。
        System.out.println("msg: " + Splitter.on("、").split("1、2、3"));//[1, 2, 3]

        //使用表达式
        System.out.println("msg: " + Splitter.onPattern("\\s+").split("1 \t 2 3"));//[1, 2, 3]

        //支持根据长度来拆分字符串。
        System.out.println("msg: " + Splitter.fixedLength(3).split("1 2 3"));//[1, 2, 3]

        //Splitter.MapSplitter 用来拆分被拼接了的 Map 对象，返回 Map<String, String>
        Map<String, String> map = Splitter.on("#").withKeyValueSeparator(":").split("1:2#3:4");
        System.out.println("msg: " + map);


    }
}
