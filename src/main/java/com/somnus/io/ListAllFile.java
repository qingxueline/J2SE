package com.somnus.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 如何递归遍历目录
 * @Title: ListAllFile.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:58:01 
 * @version V1.0
 */
public class ListAllFile {
	/** 判断目录或文件所处的层次*/
	private static int time;// 

	public static void deepList(File file) {
		if (file.isFile() || file.listFiles().length == 0) {
			return;
		} else {
			File[] files = file.listFiles();
			files = sort(files);
			for (File f : files) {
				StringBuffer out = new StringBuffer();
				if (f.isFile()) {
					out.append(getTabs(time));
					out.append(f.getName());
				} else {
					out.append(getTabs(time));
					out.append(f.getName());
					out.append("\\");
				}
				System.out.println(out.toString());
				if (f.isDirectory()) {
					time++;
					deepList(f);
					time--;
				}
			}
		}
	}

	private static File[] sort(File[] files) {
		List<File> sorted = new ArrayList<File>();
		// 寻找到所有的目录
		for (File f : files) {
			if (f.isDirectory()) {
				sorted.add(f);
			}
		}
		// 寻找到所有的文件
		for (File f : files) {
			if (f.isFile()) {
				sorted.add(f);
			}
		}

		return sorted.toArray(new File[files.length]);
	}

	private static String getTabs(int time) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < time; i++) {
			buffer.append("\t");
		}
		return buffer.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* File file = new File("d:\\software"); */
		File file = new File("src/main/java");

		deepList(file);
	}

}
