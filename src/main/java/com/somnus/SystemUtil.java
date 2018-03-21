package com.somnus;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @Description: TODO
 * @author Somnus
 * @date 2016年1月21日 下午2:51:49 
 * @version V1.0
 */
public class SystemUtil {
	private static String ip = null;
	private static String machine = null;

	private static void init(){
		InetAddress addr = null;
	    try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		 
		ip = addr.getHostAddress().toString();//获得本机IP 
		machine = addr.getHostName().toString();//获得本机名称 
	}
	/**
	 * 
	 * @return 机器IP
	 */
	public static String getIp(){
		if(ip == null){
			init();
		}
		return ip;
	}
	/**
	 * 
	 * @return 机器编号
	 */
	public static String getMachine(){
		if(machine == null){
			init();
		}
		return machine;
	}
	
	public static void main(String[] args) {
        System.out.println(getIp());
        System.out.println(getMachine());
    }
	
}
