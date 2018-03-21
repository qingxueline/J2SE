package com.somnus.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @Title: TestFileReader.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:01 
 * @version V1.0
 */
public class TestFileReader {

    /**
     * 读取txt文件的内容
     * 
     * @param file
     *            想要读取的文件对象
     * @return 返回文件内容
     * @throws IOException 
     */
    public static String txt2String(String fileName) throws IOException {
        String result = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result = result + "\n" + s;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            br.close();
        }
        return result;
    }
    /**
     * 读取txt文件的内容
     * 
     * @param buff
     *            想要读取的文件对象
     * @return 返回文件内容
     * @throws IOException 
     */
    public String txt2String(byte[] buff) throws IOException {
        String result = "";
        BufferedReader br = null;
        try {
            InputStream in = new ByteArrayInputStream(buff, 0, buff.length);// 字节流
            InputStreamReader isr = new InputStreamReader(in);// 字符流
            br = new BufferedReader(isr);
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result = result + "\n" + s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            br.close();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String result = txt2String("src/main/resources/user.xml");
        System.out.println(result);
        String[] arr = result.split("\n");
        for(String str:arr){
            System.out.println(str);
        }
        System.out.println(arr.length);
    }

}