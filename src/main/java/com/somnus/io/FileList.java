package com.somnus.io;

import java.io.File;

/**
 * 遍历目录
 * @Title: FileList.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:57:21 
 * @version V1.0
 */
public class FileList {

	public static void main(String[] args) {
		File file = new File("src/main/resources/");
		String[] names = file.list();
		for(String data:names){
			System.out.println(data);
		}
		
		File[] f = file.listFiles();
		for(File data:f){
			System.out.println(data.getName());
		}
	}
	
}