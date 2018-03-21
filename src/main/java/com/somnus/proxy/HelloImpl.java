package com.somnus.proxy;

/** 
 * @Title: HelloImpl.java 
 * @Package com.somnus.proxy 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月3日 下午1:25:43 
 * @version V1.0 
 */
public class HelloImpl implements Hello{

    @Override
    public void say(String name) {
        System.out.println("Hello! " + name);
    }

}
