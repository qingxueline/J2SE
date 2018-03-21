package com.somnus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @Title: UniqueList.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:37:52 
 * @version V1.0
 */
public class UniqueList {
	/**
	 * List 去掉重复的值，并保持原先List顺序
	 */
    @Test
	public void removeDuplicateFromList() {
	    List<String> list = Arrays.asList("a", "b", "c","a", "c", "a");
		Set<String> set = new HashSet<String>();
		List<String> newList = new ArrayList<String>();
		for (String element:list) {
			if (set.add(element))
				newList.add(element);
		}
		System.out.println(Arrays.toString(newList.toArray()));
	}
    
    @Test
    public void removeDuplicateFromList2() {
    	List<String> list = Arrays.asList("a", "b", "c","a", "c", "a");
        List<String> newList = new ArrayList<String>();
        for (String element:list) {
            if(!newList.contains(element)) {  
                newList.add(element);  
            }
        }
        System.out.println(Arrays.toString(newList.toArray()));
    }
    
    @Test
    public void removeDuplicateFromArray(){
        String[] arr = {"a","b","c","a","c","a"};
        List<String> list = new LinkedList<String>();  
        for(int i = 0; i < arr.length; i++) {  
            if(!list.contains(arr[i])) {  
                list.add(arr[i]);  
            }
        }  
        String[] s = (String[])list.toArray(new String[list.size()]); 
        System.out.println(Arrays.toString(s));
    }
}
