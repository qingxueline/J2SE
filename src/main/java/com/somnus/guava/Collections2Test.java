package com.somnus.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.istack.internal.Nullable;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class Collections2Test {

    //filter（）：只保留集合中满足特定要求的元素
    @Test
    public void FilterDemoTest() {
        List<String> list = Lists.newArrayList("moon", "dad", "refer", "son");
        Collection<String> palindromeList = Collections2.filter(list, input -> {
            //返回等于moon的字符串
            String str = "moon";
            return str.equals(input);
        });
        System.out.println(palindromeList);

    }

    //transform（）：类型转换
    @Test
    public void TransformDemo1Test() {
        Set<Long> times = Sets.newHashSet();
        times.add(91299990701L);
        times.add(9320001010L);
        times.add(9920170621L);

        Function<Long, String> fun = new Function<Long, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Long input) {
                //处理业务逻辑..........
                System.out.println("input = " + input);
                return new SimpleDateFormat("yyyy-MM-dd").format(input);//时间格式化
            }
        };
        Collection<String> timeStrCol = Collections2.transform(times, fun);

        System.out.println(timeStrCol);
    }

    //多个transform（）组合
    @Test
    public void TransformDemo2Test() {
        List<String> list = Lists.newArrayList("abcde", "good", "happiness");

        //确保容器中的字符串长度不超过5,超过5的部分被截取
        Function<String, String> f1 = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input.length() > 5 ? input.substring(0, 5) : input;
            }
        };

        //将截取好的字符串转成大写
        Function<String, String> f2 = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input.toUpperCase();
            }
        };

        //多个组合传进去
        Function<String, String> function = Functions.compose(f1, f2);
        Collection<String> results = Collections2.transform(list, function);

        System.out.println(results);
    }

    //集合操作：交集、差集、并集
    @Test
    public void CollectionsDemoTest() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(3, 4, 5, 6);
        Sets.SetView<Integer> inter = Sets.intersection(set1, set2); //交集
        System.out.println(inter);
        Sets.SetView<Integer> diff = Sets.difference(set1, set2); //差集,在A中不在B中
        System.out.println(diff);
        Sets.SetView<Integer> union = Sets.union(set1, set2); //并集
        System.out.println(union);
    }


}
