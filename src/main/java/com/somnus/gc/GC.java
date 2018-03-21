package com.somnus.gc;

import java.util.Vector;

/**  
 * @Description: TODO
 * @author Somnus
 * @date 2016年5月15日 下午6:49:14 
 * @version 1.0 
 */
public class GC {
	
	public static Object get(int index){
		Vector<Object> v = new  Vector<Object>( 10 );
		for  ( int  i = 1 ;i < 100 ; i ++ ){
		    Object o = new  Object();
		    v.add(o);
		    o = null;
		}
		return v.get(index);
	}

	public static void main(String[] args) {
		System.out.println(get(10));
	}
}
