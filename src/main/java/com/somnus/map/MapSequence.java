package com.somnus.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hashtable.keySet()           降序
 * TreeMap.keySet()             升序
 * HashMap.keySet()             乱序
 * LinkedHashMap.keySet()       原序
*/
public class MapSequence {
	public static void main(String[] args) {
        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  Hashtable  ##");
        Hashtable<String , String> ht = new Hashtable<String , String>();
        ht.put("1", "OOO");
        ht.put("3", "OOO");
        ht.put("2", "OOO");
        ht.put("5", "OOO");
        ht.put("4", "OOO");
        for(Iterator<String> it = ht.keySet().iterator();it.hasNext();) {
            System.out.println(it.next());
        }
        
        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  TreeMap  ##");
        TreeMap<String , String> tm = new TreeMap<String , String>();
        tm.put("1", "OOO");
        tm.put("3", "OOO");
        tm.put("2", "OOO");
        tm.put("5", "OOO");
        tm.put("4", "OOO");
        for(Iterator<String> it2 = tm.keySet().iterator();it2.hasNext();) {
            System.out.println(it2.next());
        }
        
        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  HashMap  ##");
        Map<String , String> hm = new HashMap<String , String>();
        hm.put("1", "OOO");
        hm.put("3", "OOO");
        hm.put("2", "OOO");
        hm.put("5", "OOO");
        hm.put("4", "OOO");
        for(Iterator<String> it3 = hm.keySet().iterator();it3.hasNext();) {
            System.out.println(it3.next());
        }
        
        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  LinkedHashMap  ##");
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String , String>();
        lhm.put("1", "OOO");
        lhm.put("3", "OOO");
        lhm.put("2", "OOO");
        lhm.put("5", "OOO");
        lhm.put("4", "OOO");
        for(Iterator<String> it4 = lhm.keySet().iterator();it4.hasNext();)  {
            System.out.println(it4.next());
        }
    }

}
