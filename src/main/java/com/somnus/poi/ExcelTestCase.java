package com.somnus.poi;

import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2015年11月6日 上午11:22:57
 */
public class ExcelTestCase {

    /**
     * 1行标题
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        List<People> list = new ArrayList<People>();
        People p1 = new People("aaaa", 21, "北京");
        People p2 = new People("bbbb", 22, "上海");
        People p3 = new People("cccc", 23, "广州");
        People p4 = new People("dddd", 24, "成都");
        People p5 = new People("eeee", 25, "重庆");
        People p6 = new People("ffff", 26, "深圳");
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        ExcelWirter writer = new ExcelWirter();
        String[] headers = new String[]{"姓名", "年龄", "地址"};
        byte[] buff = writer.exportExcel("学生信息", headers, list, null);
        OutputStream os = new FileOutputStream(new File("E:/per.xlsx"));
        IOUtils.write(buff, os);
    }


    /**
     * 2行标题
     * @throws Exception
     */
    @Test
    public void create2() throws Exception {
        List<People> list1 = new ArrayList<>();
        People p0 = new People("人员111111111111111", 100, "北京");
        list1.add(p0);

        List<People> list2 = new ArrayList<People>();
        People p1 = new People("aaaa", 21, "北京");
        People p2 = new People("bbbb", 22, "上海");
        People p3 = new People("cccc", 23, "广州");
        People p4 = new People("dddd", 24, "成都");
        People p5 = new People("eeee", 25, "重庆");
        People p6 = new People("ffff", 26, "深圳");
        list2.add(p1);
        list2.add(p2);
        list2.add(p3);
        list2.add(p4);
        list2.add(p5);
        list2.add(p6);
        ExcelWirterTest writer = new ExcelWirterTest();
        String[] headers1 = new String[]{"姓名1", "年龄1", "地址1"};
        String[] headers2 = new String[]{"姓名2", "年龄2", "地址2"};
        byte[] buff = writer.exportExcel("学生信息", headers1, list1, headers2, list2, null);
        OutputStream os = new FileOutputStream(new File("E:/per.xlsx"));
        IOUtils.write(buff, os);
    }

    @Test
    public void Reader() {
        List<String[]> list = ExcelReader.readExcel("E:/per.xlsx");
        int i = 1;
        for (String[] arr : list) {
            if (arr.length == 8) {
                if (i == 1) {
                    System.out.println("1:" + Arrays.toString(arr));
                }
                if (i == 2) {
                    System.out.println("2:" + Arrays.toString(arr));
                }
            } else {
                if (!"序号".equals(arr[0])) {
                    System.out.println("3:" + Arrays.toString(arr));
                }
            }
//            if (i == 3) {
//                System.out.println("3:" + Arrays.toString(arr));
//            }
//            if (i > 3) {
//                System.out.println("数据：" + Arrays.toString(arr));
//            }
            i++;
        }
    }

}
