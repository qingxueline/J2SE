package com.somnus.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

/**
 * 
 * @Title: TestFileOutputStream.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午5:00:08 
 * @version V1.0
 */
public class TestFileOutputStream {
	/**
	 * @知道某个byte数组
	 * @往指定的目录生成文件
	 *             文件输出流直接write
	 * @throws IOException 
	 * @throws Exception 
	 */
    @Test
	public void bytes2file() throws IOException{
	    OutputStream os = null;
        try {
            os = new FileOutputStream(new File("target/classes/TestFileOutputStream1.txt"));
            os.write("Somnus罂粟花".getBytes(/*"UTF-8"*/));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
	}
	
	/**
	 * 知道指定的目录文件
	 * 从中读书byte数据
	 * 并且往另一目录文件写入相同的文件
	 * @throws IOException 
	 */
    @Test
	public void file2file() throws IOException{
	    InputStream is = null;
	    OutputStream os = null;
        try {
            is = new FileInputStream(new File(
                    TestFileOutputStream.class.getClassLoader().getResource("build.xml").getPath()));
            os = new FileOutputStream(new File("target/classes/TestFileOutputStream2.txt"));
            
            /**分批读，分批写*/
            byte[] buff = new byte[128];
            int len = 0;
            while((len = is.read(buff/*,0,buff.length*/)) > 0){
                os.write(buff,0,len);
                System.out.print(new String(buff,0,len));
                System.out.print("【读取到的长度："+len+"】");
            }
            
            /**①一个个读出来赋值到数组中，最后一起写入*/
            /*byte[] buff = new byte[is.available()];
            int count = 0;
            int data = 0;
            while((data = is.read())!=(-1)){
                buff[count++] = (byte)data;
                  System.out.println(data +"|"+(byte)data+"|"
                          +Integer.toBinaryString(data)+"|"+Integer.toHexString(data)+ " ");
            }
            System.out.println(new String(buff));
            os.write(buff);*/
            
            /**②一个个读出来赋值到数组中，最后一起写入*/
            /*byte[] buff = new byte[is.available()];
            for (int i = 0; i < buff.length; i++){
                buff[i] = (byte)is.read();
                System.out.println(buff[i] +"|"+(byte)buff[i]+"|"
                        +Integer.toBinaryString(buff[i])+"|"+Integer.toHexString(buff[i])+ " ");
            }
            System.out.println(new String(buff));
            os.write(buff);*/
            
            /**一次性全部读到数组中并且写入*/
            /*byte[] buff = new byte[is.available()];
            int len = is.read(buff,0,buff.length);
            System.out.println("读入长度为："+len);
            System.out.println(new String(buff));
            os.write(buff);*/
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            is.close();
            os.close();
        }
	}
	
}