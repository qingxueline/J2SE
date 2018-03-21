package com.somnus.xml;

/**
 * 
 * @Title: XmlDocument.java 
 * @Package com.somnus.xml 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:32:59 
 * @version V1.0
 */
public interface XmlDocument {
	/** 
	 *  定义XML文档建立与解析的接口 
	 * /
	
	/** 
	 *  @see 建立XML文档 
	 *  @param fileName 文件全路径名称
	 */
	public void createXml(String fileName)  throws Exception;

	/**
	 * @see 解析XML文档 
	 * @param fileName 文件全路径名称
	 */
	public void parserXml(String fileName)  throws Exception;
}
