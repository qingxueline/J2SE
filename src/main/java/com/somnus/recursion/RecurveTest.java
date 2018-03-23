package com.somnus.recursion;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecurveTest {

    static int times = 0;

    //通风父类节点查询所有子类节点
    @Test
    public void findChilds() {
        List<City> cites = City.findCites();
        List<City> resultList = new ArrayList<>();
        System.out.println("查询前集合大小:" + cites.size());
        long start = System.currentTimeMillis();
        for (City testEntity : cites) {
            times++;
            if (testEntity.getParentId() == 0) {//父级菜单开始添加
                resultList.add(testEntity);
                if (ifChilds(cites, testEntity.getId())) {//存在子集
                    List<City> childs = new ArrayList<>();
                    childs = getChildList(cites, testEntity.getId(), childs);
                    resultList.addAll(childs);
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("查询后集合大小:" + resultList.size());
        System.out.println("查询次数:" + times + "次");
        System.out.println("查询耗时:" + (end - start) + "毫秒");

        for (City testEntity : resultList) {
            String s = "";
            for (int i = 1; i < testEntity.getLevel(); i++) {
                s = s + "\t";
            }
            System.out.println(s + "I____" + testEntity.getName());
        }


    }


    //获取父id下的子集合
    private static List<City> getChildList(List<City> list, int pId, List<City> reList) {
        for (City testEntity : list) {
            times++;
            if (testEntity.getParentId() == pId) {//查询下级菜单
                reList.add(testEntity);
                if (ifChilds(list, testEntity.getId())) {
                    getChildList(list, testEntity.getId(), reList);
                }
            }
        }
        return reList;
    }

    //判断是否存在子集
    private static boolean ifChilds(List<City> list, int pId) {
        boolean flag = false;
        for (City testEntity : list) {
            times++;
            if (testEntity.getParentId() == pId) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    //通过子类节点查询所有父类节点
    @Test
    public void getSelfAndTheirParentRecordTest() {
        List<City> cites = City.findCites();
        List<City> reList = Lists.newArrayList();
        getSelfAndTheirParentRecord(cites, reList, 14);
    }


    private static List<City> getSelfAndTheirParentRecord(List<City> list, List<City> reList, int parentId) {
        while (true) {
            City city = getByParentId(list, parentId);
            if (city == null) {
                break;
            }
            reList.add(city);
            parentId = city.getParentId();
        }
        return reList;
    }

    public static City getByParentId(List<City> list, int parentId) {
        City city = null;
        for (City c : list) {
            if (c.getId() == parentId) {
                city = c;
                break;
            }
        }
        System.out.println("..........city = " + city);
        return city;
    }

}
