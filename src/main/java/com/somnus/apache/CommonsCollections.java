package com.somnus.apache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @Title: CommonsCollections.java 
 * @Package com.somnus.apache 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月12日 下午5:38:59 
 * @version V1.0
 */
public class CommonsCollections {
    
	public static void main(String[] args) {

		List<String> emptyList = new ArrayList<String>();
		
		/*集合判断： 
		例1: 判断集合是否为空:*/
		System.out.println(CollectionUtils.isEmpty(emptyList));
		System.out.println(CollectionUtils.isEmpty(Arrays.asList("1", "2", "3")));

		/*例2: 判断集合是否不为空:*/
		System.out.println(CollectionUtils.isNotEmpty(emptyList));
		System.out.println(CollectionUtils.isNotEmpty(Arrays.asList("1", "2", "3")));

		
		System.out.println(CollectionUtils.union(
				Arrays.asList("1", "2", "3"), 
				Arrays.asList("2", "3", "5")));//并集{3, 2, 1, 5}
		
		System.out.println(CollectionUtils.intersection(
				Arrays.asList("1", "2", "3"), 
				Arrays.asList("2", "3", "5")));//交集{3, 2}
		
		System.out.println(CollectionUtils.disjunction(
				Arrays.asList("1", "2", "3"), 
				Arrays.asList("2", "3", "5")));//交集的补集{1, 5}
		
		System.out.println(CollectionUtils.subtract(
				Arrays.asList("2", "3", "5"), 
				Arrays.asList("1", "2", "3")));//list1与list2的差{1}
	}
}
