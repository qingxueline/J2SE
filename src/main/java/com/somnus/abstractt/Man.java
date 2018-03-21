package com.somnus.abstractt;

/**
 * @description: 
 * @author:  Somnus
 * @version: 1.0
 * @createDate: 2016年3月23日 下午3:17:14 
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年3月23日       Somnus                               1.0            
 */
public class Man extends AbstractHuman {

	public Man(String type) {
		super(type);
	}
	
	@Override
	public String toString() {
		return super.toString()+"男人";
	}

	@Override
	public void 具体业务() {
		System.out.println("我是来存钱的");
	}

}
