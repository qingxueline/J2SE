package com.somnus.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import org.junit.Test;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.guava
 * @date 2018/05/27 1:03
 * @description Function 到达的效果类似于策略设计模式,比如常用于集合转换
 * 截至JDK7，Java中也只能通过笨拙冗长的匿名类来达到近似函数式编程的效果。预计JDK8中会有所改变，但Guava现在就想给JDK5以上用户提供这类支持。
 * <p>
 * 过度使用Guava函数式编程会导致冗长、混乱、可读性差而且低效的代码。这是迄今为止最容易（也是最经常）被滥用的部分，如果你想通过函数式风格达成一行代码，致使这行代码长到荒唐，Guava团队会泪流满面。
 */
public class FunctionTest {

    Function<String, Integer> lengthFunction = new Function<String, Integer>() {
        @Override
        public Integer apply(String string) {
            return string.length();
        }
    };

    Predicate<String> allCaps = new Predicate<String>() {
        @Override
        public boolean apply(String string) {
            return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
        }
    };

    @Test
    public void test() {
        System.out.println("lengthFunction = " + lengthFunction.apply("aaaaaa"));
    }
}
