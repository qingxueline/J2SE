package com.somnus.sort;

import java.util.Comparator;

/** 
 * @description: 排序器接口(策略模式: 将算法封装到具有共同接口的独立的类中使得它们可以相互替换)
 * @author Somnus
 * date 2015年4月22日 上午10:44:59  
 */
public interface Sorter {
    /** 
     * 排序 
     * @param list 待排序的数组 
     */  
    public <T extends Comparable<T>> void sort(T[] arr);  
     
    /** 
     * 排序 
     * @param list 待排序的数组 
     * @param comp 比较两个对象的比较器 
     */  
    public <T> void sort(T[] arr,Comparator<T> comp);  
}
