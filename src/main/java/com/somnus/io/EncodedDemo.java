package com.somnus.io;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/** 
 * @Title: EncodedDemo.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年5月17日 下午1:45:24 
 * @version V1.0 
 */
public class EncodedDemo {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "美丽ABC";
		//utf-8编码中文占用3个字节，英文占用1个字节
		byte[] buff = str.getBytes(/*"utf-8"*/);
		/**举例：e7 是怎么等于-25的？*/
		//16进制的E7  ===  二进制的11100111
		//那11100111 是怎么和-25挂钩的呢
		//      二进制              十进制       十六进制
		/* 补码 1 1100111   231    E7
		 * 反码 1 1100110   
		 * 原码 1 0011001   -25    
		 */
		//1 0011001 当然就是-25咯，原来E7是补码形式的十六进制
		System.out.println(Arrays.toString(buff));
		for(byte b:buff){
			System.out.print(Integer.toHexString(b & 0xff )+" ");
		}
		
		System.out.println();
		//gbk编码中文占用2个字节，英文占用1个字节
		byte[] buff2 = str.getBytes("gbk");
		System.out.println(Arrays.toString(buff2));
		for(byte b:buff2){
			System.out.print(Integer.toHexString(b & 0xff )+" ");
		}
	}

}
