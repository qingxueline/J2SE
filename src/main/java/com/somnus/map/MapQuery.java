package com.somnus.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MapQuery{

	public static void main(String[] args){
		Map<Integer,Integer> map = new TreeMap<Integer,Integer>();
		Random random = new Random();
		for(int i = 0;i<10;i++){
			int num = random.nextInt(1000);
			
			Integer in = new Integer(num);
			
			if(map.get(in)==null){
				map.put(in, new Integer(1));
			}
			else{
				int value = ((Integer)map.get(in)).intValue();
				map.put(in, new Integer(value+1));
			}
			
		}
		
		Set<Entry<Integer, Integer>> set = map.entrySet();
		
		for(Iterator<Entry<Integer, Integer>> it = set.iterator();it.hasNext();){
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>)it.next();
			
			Integer key = (Integer)entry.getKey();
			
			Integer value = (Integer)entry.getValue();
			
			System.out.println(key+":"+value);
		}
	}

}
