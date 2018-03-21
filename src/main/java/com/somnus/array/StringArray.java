package com.somnus.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StringArray{

	public static void main(String[] args){
		// 测试union
		String[] arr1 = { "abc", "df", "abcd" };
		String[] arr2 = { "abc", "cc", "df", "d", "abc" };
		String[] result_union = union(arr1, arr2);
		System.out.println("求并集的结果如下：");
		for (String str : result_union){
			System.out.println(str);
		}
		System.out.println("---------------------可爱的分割线------------------------");
		// 测试insect
		String[] result_insect = intersect(arr1, arr2);
		System.out.println("求交集的结果如下：");
		for (String str : result_insect){
			System.out.println(str);
		}
		System.out.println("---------------------可爱的分割线------------------------");
	}

	// 求两个字符串数组的并集，利用set的元素唯一性
	public static String[] union(String[] arr1, String[] arr2){
		Set<String> set = new HashSet<String>();
		for (String str : arr1){
			set.add(str);
		}
		for (String str : arr2){
			set.add(str);
		}
		String[] result = {};
		return set.toArray(result);
	}

	// 求两个数组的交集
	public static String[] intersect(String[] arr1, String[] arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		List<String> list = new ArrayList<String>();
		for (String str : arr1){
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for (String str : arr2){
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}

		for (Iterator<Entry<String, Boolean>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String,Boolean> e = (Entry<String,Boolean>)it.next();
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}
		return list.toArray(new String[] {});
	}
}
