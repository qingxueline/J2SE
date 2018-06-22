package com.somnus.test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.somnus.uuid.Identities;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.map
 * @date 2018/06/22 11:41
 * @description 就2个字符数组的交集
 */
@Log4j
public class HashMapTest {


    public static void main(String[] args) {
        String time = Identities.get28LenNonce();
        Stopwatch stopwatch = Stopwatch.createStarted();

        String[] str1 = {"Abc", "D","bat"};
        String[] str2 = {"Ab", "Cd", "Dc", "D", "D","java","j","bat"};

        log.info(String.format("开始求交集 [%s]", time));

        List list = testArrayIntersectionE(str1, str2);
        System.out.println("交集：" + list);
        log.info(String.format("[%s] 耗时 [%s]", time, stopwatch.stop()));
    }

    public static List testArrayIntersectionE(String[] arrayA, String[] arrayB) {

        Map<String, Boolean> map = Maps.newHashMap();
        List<String> data = Lists.newArrayList();
        for (String s : arrayA) {
            map.put(s, false);
        }

        for (String s : arrayB) {
            if (map.containsKey(s)) {
                map.put(s, true);
            }
        }

        for (String s : map.keySet()) {
            if (map.get(s)) {
                data.add(s);
            }
        }

        return data;
    }


}


