package com.somnus.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年11月6日 上午11:22:57 
 * @version V1.0 
 */
public class ExcelTestCase {
    
    @Test
    public void create() throws Exception{
        List<People> list = new ArrayList<People>();
        People p1 = new People("aaaa",21);
        People p2 = new People("bbbb",22);
        People p3 = new People("cccc",23);
        People p4 = new People("dddd",24);
        People p5 = new People("eeee",25);
        People p6 = new People("ffff",26);
        list.add(p1);list.add(p2);
        list.add(p3);list.add(p4);
        list.add(p5);list.add(p6);
        ExcelWirter writer = new ExcelWirter();
        byte[] buff = writer.exportExcel("测试例子", new String[]{"姓名","年龄"}, list, null);
        OutputStream os = new FileOutputStream(new File("E:/Somnus.xlsx"));
        IOUtils.write(buff, os);
    }
}
class People {
    private String name;
    private int age;
    public People() {
        super();
    }
    public People(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
