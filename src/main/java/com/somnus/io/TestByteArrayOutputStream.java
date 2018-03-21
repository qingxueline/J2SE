package com.somnus.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 
 * @Title: TestByteArrayOutputStream.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:22 
 * @version V1.0
 */
public class TestByteArrayOutputStream {

	public static void main(String[] args) throws IOException{
		ByteArrayOutputStream baos = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = "中国abc123".getBytes(/*"UTF-8"*/);
            
            /**知道byte数组，往ByteArrayOutputStream组织数据*/
            baos.write(buffer);
            
            /**可以把ByteArrayOutputStream输出流变成byte数组*/
            byte[] result = baos.toByteArray();
            System.out.println(Arrays.toString(result));
            
            os = new FileOutputStream(new File("target/classes/TestByteArrayOutputStream.txt"));
            /**①ByteArrayOutputStream直接写入FileOutputStream*/
            /*baos.writeTo(os);*/
            /**②byte写入FileOutputStream*/
            os.write(result);
            
            bos = new BufferedOutputStream(os);
            /**用缓冲流写*/
            bos.write(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            baos.close();
            os.close();
            bos.close();
        }
	}
}