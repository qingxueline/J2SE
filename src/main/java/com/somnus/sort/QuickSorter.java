package com.somnus.sort;

import java.util.Comparator;

/** 
 * 快速排序 
 * 快速排序是使用分治法（divide-and-conquer）依选定的枢轴 
 * 将待排序序列划分成两个子序列，其中一个子序列的元素都小于枢轴， 
 * 另一个子序列的元素都大于或等于枢轴，然后对子序列重复上面的方法， 
 * 直到子序列中只有一个元素为止 
 * @author Somnus
 * date 2015年4月22日 上午10:46:31 
 */
public class QuickSorter implements Sorter {

    @Override  
    public <T extends Comparable<T>> void sort(T[] arr) {  
       quickSort(arr,0, arr.length- 1);  
    }  
    
    @Override  
    public <T> void sort(T[] arr,Comparator<T> comp) {  
       quickSort(arr,0, arr.length- 1, comp);  
    }  
    
    private <T extends Comparable<T>>void quickSort(T[] arr, int first, int last) {  
       if (last > first) {  
         int pivotIndex = partition(arr, first, last);  
         quickSort(arr,first, pivotIndex - 1);  
         quickSort(arr,pivotIndex, last);  
       }  
    }  
     
    private <T> void quickSort(T[] arr, int first, int last,Comparator<T> comp) {  
       if (last > first) {  
         int pivotIndex = partition(arr, first, last, comp);  
         quickSort(arr,first, pivotIndex - 1, comp);  
         quickSort(arr,pivotIndex, last, comp);  
       }
    }  
    
    private <T extends Comparable<T>> int partition(T[] arr, int first, int last) {  
       T pivot = arr[first];  
       int low = first + 1;  
       int high = last;  
    
       while (high > low) {  
         while (low <= high&& arr[low].compareTo(pivot) <= 0) {  
            low++;  
         }  
         while (low <= high&& arr[high].compareTo(pivot) >= 0) {  
            high--;  
         }  
         if (high > low) {  
            T temp = arr[high];  
            arr[high]= arr[low];  
            arr[low]= temp;  
         }  
       }  
    
       while (high > first&& arr[high].compareTo(pivot) >= 0) {  
         high--;  
       }  
       if (pivot.compareTo(arr[high])> 0) {  
         arr[first]= arr[high];  
         arr[high]= pivot;  
         return high;  
       }  
       else {  
         return low;  
       }  
    }  
    
    private <T> int partition(T[] arr, int first, int last,Comparator<T> comp) {  
       T pivot = arr[first];  
       int low = first + 1;  
       int high = last;  
    
       while (high > low) {  
         while (low <= high&& comp.compare(arr[low], pivot) <= 0) {  
            low++;  
         }  
         while (low <= high&& comp.compare(arr[high], pivot) >= 0) {  
            high--;  
         }  
         if (high > low) {  
            T temp = arr[high];  
             arr[high] = arr[low];  
            arr[low]= temp;  
         }  
       }  
    
       while (high > first&& comp.compare(arr[high], pivot) >= 0) {  
         high--;  
       }  
       if (comp.compare(pivot,arr[high]) > 0) {  
         arr[first]= arr[high];  
         arr[high]= pivot;  
         return high;  
       }  
       else {  
         return low;  
       }  
    }  

}
