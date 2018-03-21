package com.somnus.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 
 * @Title: TestByteArrayInputStream.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:30 
 * @version V1.0
 */
public class TestByteArrayInputStream{

	public static void main(String[] args) throws IOException{
	    byte[] buff = "中国abc123".getBytes(/*"UTF-8"*/);
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(buff/*,0,buff.length*/);
            
            /**一次性全部读出来*/
            /*byte[] b = new byte[is.available()];
            int len = is.read(b,0,b.length);
            System.out.println(Arrays.toString(b));
            System.out.println("读入长度为："+len);
            System.out.println(new String(b));*/
            
            /**一个个字节读*/
            int data = 0;
            byte[] buf = new byte[is.available()];
            int count = 0;
            while ((data = is.read()) != -1){
                buf[count++] = (byte)data;
                System.out.println(data +"|"+(byte)data+"|"
                        +Integer.toBinaryString(data)+"|"+Integer.toHexString(data)+ " ");
            }
            System.out.println(Arrays.toString(buf));
            System.out.println(new String(buf));
            
            /**或者分批读*/
            /*byte[] buf = new byte[128];
            int len = 0;
            while((len = is.read(buf))!=-1){
                System.out.println(Arrays.toString(buf));
                System.out.print(new String(buf,0,len));
                System.out.print("[读取到的长度："+len+"]");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            is.close();
        }
	}
	
}
