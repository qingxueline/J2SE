package com.somnus.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapQuery1{

	public static void main(String[] args){
		Map<String,Integer> map = new TreeMap<String,Integer>();
		String str = "GZITCASTADVANCEDTRAININGJAVASEJAVAEEANDROID";
		for(int i = 0;i<str.length();i++){
			String chart = str.substring(i,i+1);
			
			if(map.get(chart)==null){
				map.put(chart, new Integer(1));
			}
			else{
				int value = ((Integer)map.get(chart)).intValue();
				map.put(chart, new Integer(value+1));
			}
		}
		
		Set<Map.Entry<String,Integer>> set = map.entrySet();
		
		for(Iterator<Map.Entry<String,Integer>> it = set.iterator();it.hasNext();){
			Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)it.next();
			
			String key = (String)entry.getKey();
			
			Integer value = (Integer)entry.getValue();
			
			System.out.println(key+":"+value);
		}
	}

}
