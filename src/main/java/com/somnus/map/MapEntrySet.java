package com.somnus.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 遍历MAP的两种方式
 * @Title: MapEntrySet.java 
 * @Package com.somnus.map 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:06:47 
 * @version V1.0
 */
public class MapEntrySet{

	public static void main(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("aa", "AAAA");
		map.put("bb", "BBBB");
		map.put("cc", "CCCC");
		map.put("dd", "DDDD");
		//解析map的第一种方式
		for(Iterator<Entry<String, String>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String, String> entry = (Entry<String, String>)it.next();
			
			String key = (String)entry.getKey();
			
			String value = (String)entry.getValue();
			
			System.out.println(key+":"+value);
		}
		//**********************************************
		//解析map的第二种方式
		for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
			String key = (String)it.next();
			
			String value = (String)map.get(key);
			
			System.out.println(key+":"+value);
		}
	}

}
