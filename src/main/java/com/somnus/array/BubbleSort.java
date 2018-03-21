package com.somnus.array;
/**
 * 冒泡排序
 * @author Compaq
 *
 */
public class BubbleSort{
	/*
	 * 0{7,3,9,5,6,8,1}; -->3,7,9,5,6,8,1-->3,7,9,5,6,8,1-->3,7,5,9,6,8,1-->3,7,5,6,9,8,1-->3,7,5,6,8,9,1-->3,7,5,6,8,1,9  【5,6】对比
	 * 1{3,7,5,6,8,1,9}; -->3,7,5,6,8,1,9-->3,5,7,6,8,1,9-->3,5,6,7,8,1,9-->3,5,6,7,8,1,9-->3,5,6,7,1,8,9  【4,5】对比
	 * 2{3,5,6,7,1,8,9}; -->3,5,6,7,1,8,9-->3,5,6,7,1,8,9-->3,5,6,7,1,8,9-->3,5,6,1,7,8,9  【3,4】对比
	 * 3{3,5,6,1,7,8,9}; -->3,5,6,1,7,8,9-->3,5,6,1,7,8,9-->3,5,1,6,7,8,9  【2,3】对比
	 * 4{3,5,1,6,7,8,9}; -->3,1,5,6,7,8,9-->3,1,5,6,7,8,9  【1,2】对比
	 * 5{3,1,5,6,7,8,9}; -->1,3,5,6,7,8,9  【0,1】对比
	 * 6{1,3,5,6,7,8,9};
	 */
	public static int[] bubbleSort(int[] array){
		for(int i = 0;i < array.length;i++){											
			for(int j = 0; j < array.length-i-1;j++){
				if(array[j] > array[j+1]){						  
					int temp = array[j];  
					array[j] = array[j+1]; 
					array[j+1] = temp;	  
				}
			}
			System.out.println("第"+(i+1)+"趟排序");
			for(int k = 0;k < array.length;k++){
				System.out.print(array[k]+"  ");
			}
			System.out.println();
		}
		return array;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args){
		int[] array = {7,3,9,5,6,8,1};
		bubbleSort(array);
	}

}
