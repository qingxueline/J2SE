package com.somnus.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * @Title: JdomDemo.java 
 * @Package com.somnus.xml 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:32:50 
 * @version V1.0
 */
public class JdomDemo implements XmlDocument{
    
	public void parserXml(String fileName) throws Exception{
		SAXBuilder bulider = new SAXBuilder();
		
		Document doc = bulider.build(new File(fileName));
		//获得根节点
		Element root = doc.getRootElement();
		
		System.out.println("根元素: "+root.getName());
		
		//获得Account元素
	    /*Element account = root.getChild("Account");*/
		
		List<Element> accountList = root.getChildren("Account");//特指就传参
		for(Iterator<Element> iter = accountList.iterator(); iter.hasNext();)
		{
			Element account = iter.next();
			
			System.out.println("属性-所属区域: "+ account.getAttributeValue("type"));
			
			List<Attribute> listatr = account.getAttributes();
			//循环遍历属性
			for(Attribute data:listatr)
			{
				System.out.println("属性:"+data.getName()+" = "+data.getValue());
			}
			
			List<Element> listele = account.getChildren();//不特指就为空
			
			for (Element data:listele) { // 遍历<Accounts>下的节点
				
				if ("code".equals(data.getName())) // 输出code
				{
					System.out.println("卡号: " + data.getText());
				}
				else if ("pass".equals(data.getName())) // 输出pass
				{
					System.out.println("密码: " + data.getText());
				}
				else if ("name".equals(data.getName())) // 输出name
				{
					System.out.println("姓名: " + data.getText());
				}
				else if ("money".equals(data.getName())) // 输出money
				{
					System.out.println("余额: " + data.getText());
				}
			}
			System.out.println();
		}
	}
	
	public void createXml(String fileName)  throws Exception{
		Document document = new Document();
		
		Element rootElement = new Element("user");
		document.addContent(rootElement);
		
		Comment comment = new Comment("注释");
		rootElement.addContent(comment);
		
		//*******设置第一个********
		Element accountElement = new Element("Account");
		accountElement.setAttribute("type","mobile");
		rootElement.addContent(accountElement);
		
		Element name = new Element("name");
		name.setText("移动");
		accountElement.addContent(name);
		
		Element code = new Element("code");
		code.setText("10086");
		accountElement.addContent(code);
		
		Element pass = new Element("pass");
		pass.setText("123456");
		accountElement.addContent(pass);
		
		Element money = new Element("money");
		money.setText("999999999");
		accountElement.addContent(money);
		
		//*******设置第二个********
		Element accountElement2 = new Element("Account");
		accountElement2.setAttribute("type","unicom");
		rootElement.addContent(accountElement2);
		
		Element name2 = new Element("name");
		name2.setText("联通");
		accountElement2.addContent(name2);
		
		Element code2 = new Element("code");
		code2.setText("10010");
		accountElement2.addContent(code2);
		
		Element pass2 = new Element("pass");
		pass2.setText("123456");
		accountElement2.addContent(pass2);
		
		Element money2 = new Element("money");
		money2.setText("88888888");
		accountElement2.addContent(money2);
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		
		out.output(document, new FileOutputStream(fileName));
		
	}
}
