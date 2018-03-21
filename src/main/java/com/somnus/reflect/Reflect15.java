package com.somnus.reflect;

import java.lang.reflect.Array;

public class Reflect15 {

    /**
     * 通过反射拆包，如果是数组就拆包。
     * 应用中没有办法的到数组的类型。因为Object[] a = new Object[]{};可以放所有的数据类型
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] temp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        prinObject(temp);
        prinObject("xyj");
    }

    private static void prinObject(Object obj) {
        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                System.out.println(Array.get(obj, i));
            }
        } else {
            System.out.println("obj = " + obj);
        }
    }
}
