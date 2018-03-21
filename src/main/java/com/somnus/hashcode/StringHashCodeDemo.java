package com.somnus.hashcode;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2016年2月26日 下午2:37:39 
 * @version V1.0 
 */
public class StringHashCodeDemo {
    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }

}
