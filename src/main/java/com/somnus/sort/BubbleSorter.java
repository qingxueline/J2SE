package com.somnus.sort;

import java.util.Arrays;
import java.util.Comparator;

/** 
 * @description: 冒泡排序  
 * @author Somnus
 * date 2015年4月22日 上午10:46:31  
 */
public class BubbleSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] arr) {
        boolean swapped = true;  
        for(int i = 1; i < arr.length && swapped;i++) {
          swapped= false;  
          for(int j = 0; j < arr.length - i; j++) {  
             if(arr[j].compareTo(arr[j+ 1]) > 0 ) {  
                T temp = arr[j];  
                arr[j]= arr[j + 1];  
                arr[j+ 1] = temp;  
                swapped= true;  
             }
          }
        }
    }

    @Override
    public <T> void sort(T[] arr, Comparator<T> comp) {
        boolean swapped = true;  
        for(int i = 1; i < arr.length && swapped;i++) {
          swapped= false;
          for(int j = 0; j < arr.length - i; j++) {
             if(comp.compare(arr[j],arr[j + 1]) > 0 ) {
                T temp = arr[j];
                arr[j]= arr[j + 1];
                arr[j+ 1] = temp;
                swapped= true;
             }
          }
        }
    }
    
    public static void main(String[] args) {
    	BubbleSorter s = new BubbleSorter();
    	Integer[] array = new Integer[]{7,3,9,5,6,8,1};
    	s.sort(array);
    	System.out.println(Arrays.toString(array));
    	
    	Integer[] array2 = new Integer[]{7,3,9,5,6,8,1};
    	s.sort(array2, new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
    	});
    	System.out.println(Arrays.toString(array2));
	}

}
