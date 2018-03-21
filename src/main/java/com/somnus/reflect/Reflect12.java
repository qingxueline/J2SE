package com.somnus.reflect;

import java.lang.reflect.Field;

public class Reflect12 {
    /**
     * 通过反射操作属性
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Class<?> demo = null;

        Object obj = null;

        demo = Class.forName("com.somnus.reflect.People");

        obj = demo.newInstance();

        Field field = demo.getDeclaredField("sex");

        field.setAccessible(true);

        field.set(obj, "男");

        System.out.println(field.get(obj));
    }

}
