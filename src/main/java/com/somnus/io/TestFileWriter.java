package com.somnus.io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @Title: TestFileWriter.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:59:48 
 * @version V1.0
 */
public class TestFileWriter {

	public static void main(String[] args) throws IOException {
		FileWriter fw = null;
		try {
			fw =new FileWriter("src/main/resources/filewriter.txt");
			fw.write("hello");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
		    fw.close();
		}
	}
	
}