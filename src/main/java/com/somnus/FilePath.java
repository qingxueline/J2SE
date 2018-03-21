package com.somnus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

/** 
 * @description: 获取文件在服务器（本地编译好的项目）中的路劲 
 * @author Somnus
 * date 2015年4月1日 下午5:56:46  
 */
public class FilePath {
    
    @Test
    public void print(){
        String path = System.getProperty("user.dir");
        System.out.println(path);
    }
    
    @Test
    public void print1(){
        /**
         *  path不以’/'开头时，默认是从此类所在的包下取资源；
         * /E:/github/J2SE/target/test-classes/com/somnus/
         */
        String path = getClass().getResource("").getPath();
        System.out.println(path);
    }
    
    @Test
    public void print2(){
        /**
         * path以’/'开头时，则是从ClassPath根下获取；
         * /E:/github/J2SE/target/test-classes//
         */
        String path = getClass().getResource("/").getPath();
        System.out.println(path);
    }
    
    @Test
    public void print3(){
        /**
         * /E:/github/J2SE/target/classes/excel/80034.xls
         */
        String path = getClass().getResource("/excel/80034.xls").getPath();
        System.out.println(path);
    }
    
    /*********************************getClass().getClassLoader()*********************************************/
    
    @Test
    public void print4(){
    	/**
    	 * path为空就直接是从ClassPath根下获取
    	 * /E:/github/J2SE/target/test-classes
    	 */
        String path = getClass().getClassLoader().getResource("").getPath();
        System.out.println(path);
    }
    
    @Test
    public void print5(){
        /*error*/
    	/**
    	 * path不能以’/'开头时；
    	 * 加上/ 就会报错
    	 */
        String path = getClass().getClassLoader().getResource("/").getPath();
        System.out.println(path);
    }
    
    @Test
    public void print6(){
    	/**
    	 * /E:/github/J2SE/target/classes/excel/80034.xls
    	 */
        String path = getClass().getClassLoader().getResource("excel/80034.xls").getPath();
        System.out.println(path);
    }
    
    @Test
    public void print7(){
        /*/E:/github/J2SE/target/classes/META-INF*/
        String path = getClass().getClassLoader().getResource("META-INF").getPath();
        System.out.println(path);
    }
    
    @Test
    public void prin() throws IOException{
        InputStream is = getClass().getClassLoader().getResourceAsStream("user.xml");
        try {
            byte[] buf = new byte[128];
            int len = 0;
            while((len = is.read(buf))!=-1){
                System.out.println(Arrays.toString(buf));
                System.out.print(new String(buf,0,len));
                System.out.print("[读取到的长度："+len+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            is.close();
        }
    }
}
