package com.somnus.guava;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class CollectionTest {

    /**
     * 不可变集合
     * 当对象被不可信的库调用时，不可变形式是安全的；
     * •不可变对象被多个线程调用时，不存在竞态条件问题
     * <p>
     * •不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
     * <p>
     * <p>
     * •不可变对象因为有固定不变，可以作为常量来安全使用。
     */
    @Test
    public void immutableDemoTest() {
        ImmutableSet<String> set = ImmutableSet.of("a", "b", "c", "d");//不可变集合
        ImmutableSet<String> set1 = ImmutableSet.copyOf(set);
        ImmutableSet<String> set2 = ImmutableSet.<String>builder().addAll(set).add("e").build();
        System.out.println(set2);
        ImmutableList<String> list = set.asList();
        System.out.println("list = " + list);

    }

    //新型集合类
    //Multiset可统计一个词在文档中出现了多少次
    @Test
    public void MultiSetDemoTest() {
        Multiset<String> set = LinkedHashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("a");
        set.setCount("a", 5); //添加或删除指定元素使其在集合中的数量是count
        System.out.println("集合 a 数量：" + set.count("a")); //给定元素在Multiset中的计数
        System.out.println(set);
        System.out.println("集合总数(包含重复)：" + set.size()); //所有元素计数的总和,包括重复元素
        System.out.println("集合总数(不重复)：" + set.elementSet().size()); //所有元素计数的总和,不包括重复元素
        set.clear(); //清空集合
        System.out.println("清空集合后：" + set);
    }

    //Multimap:一键多值的Map
    //Multimap可以很容易地把一个键映射到多个值。换句话说，Multimap是把键映射到任意多个值的一般方式。
    @Test
    public void MultiMapDemoTest() {
        Multimap<String, Object> map = HashMultimap.create(); //Multimap是把键映射到任意多个值的一般方式
        map.put("name", "张"); //key相同时不会覆盖原value
        map.put("name", "李");
        map.put("name", "朱");
        map.put("1", "1");
        map.put("2", "2");
        map.removeAll("name");
        System.out.println(map); //{a=[1, 2, 3]}
        System.out.println("集合：" + map.get("name")); //返回的是集合
        Collection c = map.get("name");
        System.out.println(c.size());
        System.out.println("集合个数:" + map.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println("不同key的个数：" + map.keySet().size()); //返回不同key的个数
        Map<String, Collection<Object>> mapView = map.asMap();
        System.out.println(mapView);
    }


    @Test
    public void BitMapDemoTest() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("sina", "sina.com");
        biMap.put("qq", "qq.com");
        biMap.put("sina", "sina.cn"); //会覆盖原来的value
       /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
        biMap.put("tecent", "qq.com"); //抛出异常
        biMap.forcePut("tecent", "qq.com"); //强制替换key
        System.out.println(biMap);
        System.out.println(biMap.inverse().get("sina.com")); //通过value找key
        System.out.println(biMap.inverse().inverse() == biMap); //true

    }


    //Table它有两个支持所有类型的键：”行”和”列”。
    @Test
    public void TableDemoTest() {
        //记录学生在某门课上的成绩
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("jack", "java", 100);
        table.put("jack", "c", 90);
        table.put("mike", "java", 93);
        table.put("mike", "c", 100);
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        for (Table.Cell<String, String, Integer> cell : cells) {
            System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
        }
        System.out.println(table.row("jack"));
        System.out.println(table);
        System.out.println(table.rowKeySet());
        System.out.println(table.columnKeySet());
        System.out.println(table.values());
    }


}
