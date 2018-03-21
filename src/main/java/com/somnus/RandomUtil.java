package com.somnus;

import java.util.Random;

/**
 * 
 * @Title: RandomUtil.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:35:23 
 * @version V1.0
 */
public class RandomUtil {
    
	static int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	/**
	 * 1+5=6  2+5=7  3+5=8  4+5=9  5+5=10
	 * 6(0)+5=5  7(1)+5=6  8(2)+5=7  9(3)+5=8  10(4)+5=9
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static int generate(int min,int max){
		Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
	}
	
	public static long generate2(int min,int max){
        return Math.round(Math.random()*(max-min)+min);
	}
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.generate(5, 10));
		System.out.println(RandomUtil.generate2(5, 10));
	}
}
