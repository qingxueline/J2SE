package com.somnus.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

public class PropertiesTest{
    
	// 根据key读取value
    @Test
	public void readValue(){
		Properties properties = new Properties();
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(new File("src/main/resources/info.properties")));
			properties.load(in);
			String value = properties.getProperty("username");
			System.out.println("username" +"="+ value);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	// 读取properties的全部信息
    @Test
	public void readAllProperties(){
		Properties props = new Properties();
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(new File("src/main/resources/info.properties")));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()){
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key  +"="+  Property);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	// 写入properties信息
    @Test
	public void writeProperties(){
		Properties properties = new Properties();
		try{
			InputStream is = new FileInputStream("src/main/resources/info.properties");
			// 从输入流中读取属性列表（键和元素对）
			properties.load(is);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream os = new FileOutputStream("src/main/resources/info.properties");
			properties.setProperty("age", "21");
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			properties.store(os, "Update '" + "age" + "' value");
		}
		catch (IOException e){
			System.err.println("Visit " + "src/main/resources/info.properties" + " for updating "
					+ "age" + " value error");
		}
	}

}
