package com.somnus.recursion;

import com.google.common.collect.Lists;

import java.util.List;

public class City {

    private static List<City> cites = Lists.newArrayList();
    //id
    private int id;
    //名称
    private String name;
    //父类id
    private int parentId;
    //层次级别
    private int level;

    public City(int id, String name, int parentId, int level) {
        super();
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Location [id=" + id + ", name=" + name + ", parentId=" + parentId + ", level=" + level + "]";
    }


   //这里模拟一个菜单表的所有数据。
    static {
        City l = new City(1, "北京市", 0, 1);
        City l2 = new City(3, "朝阳区", 1, 2);
        City l4 = new City(5, "大郊亭中街", 3, 3);
        City l11 = new City(12, "二号院", 5, 4);
        City l12 = new City(13, "三号楼", 12, 5);
        City l13 = new City(14, "四号楼", 12, 5);
        City l5 = new City(6, "大望路", 3, 3);
        City l6 = new City(7, "南磨房路", 3, 3);
        City l3 = new City(4, "海淀区", 1, 2);
        City l7 = new City(8, "唐家岭路", 4, 3);
        City l8 = new City(9, "上地路", 4, 3);
        City l1 = new City(2, "天津市", 0, 1);
        City l9 = new City(10, "红桥区", 2, 2);
        City l10 = new City(11, "北辰区", 2, 2);

        cites.add(l2);
        cites.add(l);
        cites.add(l1);
        cites.add(l3);
        cites.add(l10);
        cites.add(l9);
        cites.add(l5);
        cites.add(l4);
        cites.add(l7);
        cites.add(l6);
        cites.add(l8);
        cites.add(l12);
        cites.add(l11);
        cites.add(l13);
    }

    public static List<City> findCites() {
        return cites;
    }

}
