package com.somnus.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * MapJoiner 实现为 Joiner 的一个静态内部类，它的构造函数和 Joiner 一样也是私有，只能通过 Joiner#withKeyValueSeparator 来生成实例。
 * 类似地，MapJoiner 也实现了 appendTo 方法和一系列的重载，还用 join 方法对 appendTo 做了封装。
 * MapJoiner 整个实现和 Joiner 大同小异，在实现中大量使用 Joiner 的 toString 方法来保证空指针保护行为和初始化时的语义一致。
 * <p>
 * MapJoiner 也实现了一个 useForNull 方法，这样的好处是，在获取 MapJoiner 之后再去设置空指针保护，和获取 MapJoiner 之前就设置空指针保护，
 * 是等价的，用户无需去关心顺序问题。
 * <p>
 * <p>
 * Created by lyl on 2018/3/22 0022.
 */
public class JoinerTest {
    @Test
    public void spliceTest() {
        //使用逗号并接字符串
        String msg = Joiner.on("、").join(1, 2, 3, 4, "一起走！");
        System.out.println("result:" + msg);//result:1、2、3、4、一起走！

        StringBuilder sb = new StringBuilder("result:");
        Joiner.on("、").appendTo(sb, 1, 2, 3);
        System.out.println(sb);//result:1、2、3


        //忽略空指针
        msg = Joiner.on('、').skipNulls().join(1, null, 3);//1 3
        System.out.println("result:" + msg);//result:1、3

        //将空指针变为某个指定的值
        msg = Joiner.on('、').useForNull("None").join(1, null, 3);//1 None 3
        System.out.println("result:" + msg);//result:1、None、3

        //MapJoiner 是 Joiner 的内部静态类，用于帮助将 Map 对象拼接成字符串。
        Map<String,String> map = Maps.newHashMap();
        map.put("username","killy");
        map.put("password","123456");

        msg = Joiner.on("&").withKeyValueSeparator("=").join( ImmutableMap.copyOf(map));
        System.out.println("result:" + msg);//result:password=123456&username=killy

        msg = Joiner.on("&").withKeyValueSeparator("=").join(ImmutableMap.of("username", "killy", "password", "123456"));
        System.out.println("result:" + msg);//result:password=123456&username=killy

    }
}
