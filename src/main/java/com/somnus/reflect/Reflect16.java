package com.somnus.reflect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * 反射的综合应用
 */
public class Reflect16 {

    public static void main(String[] args) {
        Collection collections = new ArrayList();
        ReflectPoint pt1= new ReflectPoint(3,3);
        ReflectPoint pt2= new ReflectPoint(3,5);
        ReflectPoint pt3= new ReflectPoint(3,3);
        collections.add(pt1);
        collections.add(pt2);
        collections.add(pt3);
        collections.add(pt1);
        System.out.println("size= " + collections.size());
    }
}
