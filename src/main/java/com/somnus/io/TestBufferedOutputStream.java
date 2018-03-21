package com.somnus.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 
 * @Title: TestBufferedOutputStream.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:39 
 * @version V1.0
 */
public class TestBufferedOutputStream {
	
	public static void main(String[] args) throws Exception {
		OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            os = new FileOutputStream("target/classes/TestBufferedOutputStream.txt");
            bos = new BufferedOutputStream(os);
            bos.write("http://google.com/".getBytes(/*"UTF-8"*/));
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            bos.close();
            os.close();
        }
	}
	
}