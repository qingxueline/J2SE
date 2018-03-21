package com.somnus.xml;

import java.io.CharArrayWriter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

/**
 * 
 * @Title: Dom4jXpathDemo.java 
 * @Package com.somnus.xml 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:32:19 
 * @version V1.0
 */
public class Dom4jXpathDemo {
    
    private static final String PATH = "user.xml";
    
    @Test
    public void buildXml() throws Exception{
        SAXReader reader = new SAXReader();
        String path = getClass().getClassLoader().getResource(PATH).getPath();
        Document document = reader.read(new File(path));
        //填充XML
        document.selectSingleNode("/user/Account[1]/name").setText("电信");
        document.selectSingleNode("/user/Account[1]/code").setText("10000");
        document.selectSingleNode("/user/Account[1]/pass").setText("123456");
        document.selectSingleNode("/user/Account[1]/money").setText("777777777");
        document.selectSingleNode("/user/Account[2]/name").setText("电信");
        document.selectSingleNode("/user/Account[2]/code").setText("10000");
        document.selectSingleNode("/user/Account[2]/pass").setText("123456");
        document.selectSingleNode("/user/Account[2]/money").setText("777777777");
        //返回XML
        CharArrayWriter arrWriter = new CharArrayWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter xmlWriter = new XMLWriter(arrWriter, format);
        String xmlStr;
        try {
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlStr = arrWriter.toString();
            //过滤掉换行符和标签之间的空格与换行
            Pattern p = Pattern.compile("\\>\\s*\\<");      
            Matcher m = p.matcher(xmlStr);      
            xmlStr = m.replaceAll("><");
        } finally{
            xmlWriter.close();
        }
        System.out.println(xmlStr);
    }
    
    @Test
    public void parserXml() throws Exception{
        SAXReader reader = new SAXReader();
        /*Document receiveDoc = DocumentHelper.parseText(result);*/
        String path = getClass().getClassLoader().getResource("user.xml").getPath();
        Document document = reader.read(new File(path));
        //第①个Account节点
        System.out.println("name:"+document.selectSingleNode("/user/Account[1]/name").getText());
        System.out.println("code:"+document.selectSingleNode("/user/Account[1]/code").getText());
        System.out.println("pass:"+document.selectSingleNode("/user/Account[1]/pass").getText());
        System.out.println("money:"+document.selectSingleNode("/user/Account[1]/money").getText());
        //第②个Account节点
        System.out.println("name:"+document.selectSingleNode("/user/Account[2]/name").getText());
        System.out.println("code:"+document.selectSingleNode("/user/Account[2]/code").getText());
        System.out.println("pass:"+document.selectSingleNode("/user/Account[2]/pass").getText());
        System.out.println("money:"+document.selectSingleNode("/user/Account[2]/money").getText());
    }
}
