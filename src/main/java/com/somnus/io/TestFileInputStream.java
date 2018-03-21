package com.somnus.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * @Title: TestFileInputStream.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:15 
 * @version V1.0
 */
public class TestFileInputStream {
    
    /**
     * @根据指定目录文件
     * @获得指定文件的byte数组
     *          边从文件输入流读边往字节输出流中写
     * @throws IOException 
     */
    @Test
    public byte[] file2bytes() throws IOException{
        byte[] buffer = null;
        ByteArrayOutputStream bos = null;
        InputStream is = null;
        try {
            bos = new ByteArrayOutputStream();
            is = new FileInputStream(new File("src/main/resources/build.xml"));
            byte[] buff = new byte[128];
            int len = 0;;
            /*
             * 读取FileInputStream的输入流到ByteArrayOutputStream中
             * 是为了得到byte数组
             */
            while ((len = is.read(buff/*,0,buff.length*/)) != -1) {
                bos.write(buff, 0, len);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            is.close();
            bos.close();
        }
        System.out.println(Arrays.toString(buffer));
        return buffer;
    }
    
    /**
     * 三种方式读取一个流
     * @throws IOException
     */
    @Test
    public void readStream() throws IOException{
        InputStream is = null;
        try {
            is = new URL("https://www.baidu.com/").openStream();
            
            /**一次性全部读出来*/
            /*byte[] b = new byte[is.available()];
            int len = is.read(b,0,b.length);
            System.out.println("读入长度为："+len);
            System.out.println(new String(b));*/
            
            /**或者一个一个读*/
            /*byte[] b = new byte[is.available()];
            for (int i = 0; i < b.length; i++){
                b[i]=(byte)is.read();
                System.out.println(b[i] +"|"+(byte)b[i]+"|"
                        +Integer.toBinaryString(b[i])+"|"+Integer.toHexString(b[i])+ " ");
            }
            System.out.println(new String(b));*/
            
            /**或者分批读*/
            byte[] buff = new byte[128];
            int len = 0;
            while((len = is.read(buff/*,0,buff.length*/))!=-1){
                System.out.print(new String(buff,0,len));
                System.out.print("【读取到的长度："+len+"】");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            is.close();
        }
    }
	
}