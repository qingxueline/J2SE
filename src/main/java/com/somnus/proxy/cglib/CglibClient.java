package com.somnus.proxy.cglib;

/** 
 * @description: TODO 
 * @author Somnus
 * @date 2015年3月9日 上午9:05:41  
 */
public class CglibClient {
	public static void main(String[] args) {
	    CglibProxy cglib = new CglibProxy();
	    
	    HelloImpl proxy = (HelloImpl)cglib.getProxy(new HelloImpl());
		proxy.say("Somnus");
		/**-->>com.somnus.cglib.ProxyObject$$EnhancerByCGLIB$$1166e3aa@404de8d8*/
		/*System.out.println("-->>"+proxy);*/
		
		System.out.println("*****************************************************************");
		
		HelloImpl proxy2 = (HelloImpl)cglib.getProxy(HelloImpl.class);
		proxy2.say("Somnus");
	}
}
