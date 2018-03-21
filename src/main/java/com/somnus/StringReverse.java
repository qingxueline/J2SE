package com.somnus;

/**
 * 三种方式实现字符串反转
 * @Title: StringReverse.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:37:00 
 * @version V1.0
 */
public class StringReverse {

    /**
     * 用循环遍历char数组的方法实现字符串倒转
     * @param s
     * @return
     */
    public static String reverse1(String s) {
        char[] array = s.toCharArray();
        String reverse = "";
        for (int i = array.length - 1; i >= 0; i--) {
            reverse += array[i];
        }
        return reverse;
    }

    /**
     * 用JDK中的StringBuffer自带方法实现字符串倒转
     * @param s
     * @return
     */
    public static String reverse2(String s) {
        return new StringBuffer(s).reverse().toString();
    }
    /**
     * 用递归实现字符串倒转
     * @param s
     * @return
     */
    public static String reverse3(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }
        return reverse3(s.substring(1)) + s.charAt(0);
    }

    public static void main(String[] args) {
        System.out.println(reverse1(" I love java"));
        System.out.println(reverse2(" I love java"));
        System.out.println(reverse3(" I love java"));
    }
}
