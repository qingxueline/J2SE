package com.somnus.sort;

import java.util.Comparator;

/** 
 * 归并排序 
 * 归并排序是建立在归并操作上的一种有效的排序算法。 
 * 该算法是采用分治法（divide-and-conquer）的一个非常典型的应用， 
 * 先将待排序的序列划分成一个一个的元素，再进行两两归并， 
 * 在归并的过程中保持归并之后的序列仍然有序。 
 * @author Somnus
 * date 2015年4月22日 上午10:46:31 
 */
public class MergeSorter implements Sorter {

    @Override  
    public <T extends Comparable<T>>void sort(T[] arr) {  
       T[]temp = (T[]) new Comparable[arr.length];  
       mSort(arr,temp, 0, arr.length- 1);  
    }  
     
    private <T extends Comparable<T>>void mSort(T[] arr, T[] temp, int low, int high) {  
       if(low == high) {  
         return ;  
       }  
       else {  
         int mid = low + ((high -low) >> 1);  
         mSort(arr,temp, low, mid);  
         mSort(arr,temp, mid + 1, high);  
         merge(arr,temp, low, mid + 1, high);  
       }  
    }  
     
    private <T extends Comparable<T>>void merge(T[] arr, T[] temp, int left, int right, int last) {  
       int j = 0;   
         int lowIndex = left;   
        int mid = right - 1;   
         int n = last - lowIndex + 1;   
         while (left <= mid && right <= last){   
             if (arr[left].compareTo(arr[right]) < 0){   
                 temp[j++] = arr[left++];   
             } else {   
                 temp[j++] = arr[right++];   
             }   
         }   
         while (left <= mid) {   
             temp[j++] = arr[left++];   
         }   
         while (right <= last) {   
             temp[j++] = arr[right++];   
         }   
         for (j = 0; j < n; j++) {   
             arr[lowIndex + j] = temp[j];   
         }   
    }  
    
    @Override  
    public <T> void sort(T[] arr,Comparator<T> comp) {  
       T[]temp = (T[]) new Comparable[arr.length];  
       mSort(arr,temp, 0, arr.length- 1, comp);  
    }  
     
    private <T> void mSort(T[] arr, T[]temp, int low, int high, Comparator<T> comp) {  
       if(low == high) {  
         return ;  
       }  
       else {  
         int mid = low + ((high -low) >> 1);  
         mSort(arr,temp, low, mid, comp);  
         mSort(arr,temp, mid + 1, high, comp);  
         merge(arr,temp, low, mid + 1, high, comp);  
       }  
    }  
     
    private <T> void merge(T[] arr, T[]temp, int left, int right, int last, Comparator<T> comp) {  
       int j = 0;   
         int lowIndex = left;   
         int mid = right - 1;   
         int n = last - lowIndex + 1;   
         while (left <= mid && right <= last){   
             if (comp.compare(arr[left], arr[right]) <0) {   
                 temp[j++] = arr[left++];   
             } else {   
                 temp[j++] = arr[right++];   
             }   
         }   
         while (left <= mid) {   
             temp[j++] = arr[left++];   
         }   
         while (right <= last) {   
             temp[j++] = arr[right++];   
         }   
         for (j = 0; j < n; j++) {   
             arr[lowIndex + j] = temp[j];   
         }   
    }  

}
