package com.somnus.xml;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * @Title: Dom4jDemo.java 
 * @Package com.somnus.xml 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:32:03 
 * @version V1.0
 */
public class Dom4jDemo implements XmlDocument{
    
	@SuppressWarnings("unchecked")
	public void parserXml(String fileName) throws Exception{
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(new File(fileName));
		
		Element root = document.getRootElement();
		//**************************1*********************************************
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) 
		{
			Element element = it.next();
			
			if ("Account".equals(element.getName())) 
			{
				String account = element.getName();
				System.out.println("account:"+account);
				System.out.println("属性-所属区域: " + element.attributeValue("type"));
				//*************************
				//获取单个
				//*************************
				System.out.println(element.elementText("code"));
				System.out.println(element.element("code").getText());
				
				for(Iterator<Element> child = element.elementIterator(); child.hasNext();)
				{
					Element el = child.next();
					if ("code".equals(el.getName())) // 输出code
					{
						System.out.println("卡号: " + el.getText());
					}
					else if ("pass".equals(el.getName())) // 输出pass
					{
						System.out.println("密码: " + el.getText());
					}
					else if ("name".equals(el.getName())) // 输出name
					{
						System.out.println("姓名: " + el.getText());
					}
					else if ("money".equals(el.getName())) // 输出money
					{
						System.out.println("余额: " + el.getText());
					}
				}
				System.out.println();
			}
		}
		//**************************2*********************************************
		List<Element> accountList = root.elements("Account");//特指就传参
		for (Iterator<Element> it = accountList.iterator(); it.hasNext();) 
		{
			Element element = it.next();

			String account = element.getName();
			System.out.println("account:"+account);
			System.out.println("属性-所属区域: " + element.attributeValue("type"));
			
			List<Element> list = element.elements();//不特指就为空
			for(Iterator<Element> child = list.iterator(); child.hasNext();)
			{
				Element el = child.next();
				if ("code".equals(el.getName())) // 输出code
				{
					System.out.println("卡号: " + el.getText());
				}
				else if ("pass".equals(el.getName())) // 输出pass
				{
					System.out.println("密码: " + el.getText());
				}
				else if ("name".equals(el.getName())) // 输出name
				{
					System.out.println("姓名: " + el.getText());
				}
				else if ("money".equals(el.getName())) // 输出money
				{
					System.out.println("余额: " + el.getText());
				}
			}
			System.out.println();
		}
	}

	public void createXml(String filename) throws Exception{
		//创建文档并设置文档的根元素节点：第一种方式
//		Document document = DocumentHelper.createDocument();
//		Element root = DocumentHelper.createElement("student");
//		document.setRootElement(root);
		
		//创建文档并设置文档的根元素节点：第二种方式
		Element rootElement = DocumentHelper.createElement("user");
		//注释
		rootElement.addComment("***************************");
		
		Document document = DocumentHelper.createDocument(rootElement);
		
		//*******设置第一个********
		Element accountElement = rootElement.addElement("Account");
		//属性
		accountElement.addAttribute("type", "mobile");
		
		Element nameElement = accountElement.addElement("name");
		nameElement.setText("移动");
		
		Element codeElement = accountElement.addElement("code");
		codeElement.setText("10086");
		Element passElement = accountElement.addElement("pass");
		passElement.setText("123456");
		
		Element moneyElement = accountElement.addElement("money");
		moneyElement.setText("999999999");
		
		
		//*******设置第二个********
		Element accountElement2 = rootElement.addElement("Account");
		//属性
		accountElement2.addAttribute("type", "mobile");
		
		Element nameElement2 = accountElement2.addElement("name");
		nameElement2.setText("联通");
		
		Element codeElement2 = accountElement2.addElement("code");
		codeElement2.setText("10010");
		
		Element passElement2 = accountElement2.addElement("pass");
		passElement2.setText("123456");
		
		Element moneyElement2 = accountElement2.addElement("money");
		moneyElement2.setText("88888888");
		
		
		OutputFormat format = new OutputFormat("    ",true);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(filename)),format);
		
		writer.write(document);
		
		writer.close();
	}
}
   
 
