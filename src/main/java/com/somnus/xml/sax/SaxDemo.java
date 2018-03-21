package com.somnus.xml.sax;

import java.io.File;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.somnus.xml.XmlDocument;

/**
 * 
 * @Title: SaxDemo.java 
 * @Package com.somnus.xml.sax 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:31:28 
 * @version V1.0
 */
public class SaxDemo implements XmlDocument{
    
	public void parserXml(String fileName) throws Exception{
		//获得SAX解析器工厂实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//获得SAX解析器实例
		SAXParser parser = factory.newSAXParser();
		
		//开始进行解析
		parser.parse(new File(fileName),new DefaultHandler(){
			private Stack<String> stack = new Stack<String>();
			private String name;
			private String code;
			private String pass;
			private String money;
			@Override
			public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException{
				stack.push(qName);
				for(int i = 0;i<attributes.getLength();i++)
				{
					String attrName = attributes.getQName(i);
					String attrValue = attributes.getValue(i);
					System.out.println(attrName+"="+attrValue);
				}
			}
			@Override
			public void characters(char[] ch, int start, int length)throws SAXException{
				String tag = stack.peek();
				if("name".equals(tag))
				{
					name = new String(ch,start,length);
				}
				else if("code".equals(tag))
				{
					code = new String(ch,start,length);
				}
				else if("pass".equals(tag))
				{
					pass = new String(ch,start,length);
				}
				else if("money".equals(tag))
				{
					money = new String(ch,start,length);
				}
			}
			@Override
			public void endElement(String uri, String localName, String qName)throws SAXException{
				stack.pop();
				if("Account".equals(qName))
				{
					System.out.println("姓名: "+name);
					System.out.println("卡号: "+code);
					System.out.println("密码 "+pass);
					System.out.println("余额: "+money);
					System.out.println();
				}
			}
		});
	}

	public void createXml(String fileName) {
		System.out.println(fileName);
	}
}