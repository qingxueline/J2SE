package com.somnus.security;

/** 
 * @Title: Base64Helper.java 
 * @Package com.somnus.cipher 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月5日 下午4:53:46 
 * @version V1.0 
 */
public class Base64Helper {
  
    /** 
     * BASE64解密 
     * sun.misc.BASE64Encoder 是sun内部使用类，并没有在java api中公开过，
     * 所以使用这些方法是不安全的，将来随时可能会从中去除，所以相应的应该使用替代的对象及方法
     * org.apache.commons.codec.binary.Base64类
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new sun.misc.BASE64Decoder()).decodeBuffer(key);  
    }  
  
    /** 
     * BASE64加密 
     * sun.misc.BASE64Encoder 是sun内部使用类，并没有在java api中公开过，
     * 所以使用这些方法是不安全的，将来随时可能会从中去除，所以相应的应该使用替代的对象及方法
     * org.apache.commons.codec.binary.Base64类
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new sun.misc.BASE64Encoder()).encodeBuffer(key);  
    }  
  
    public static void main(String[] args) throws Exception {
        String inputStr = "简单加密";  
        System.out.println("原文:" + inputStr);  
        
        String code = Base64Helper.encryptBASE64(inputStr.getBytes());  
        System.out.println("BASE64加密后:" + code);
        
        byte[] output = Base64Helper.decryptBASE64(code);  
        System.out.println("BASE64解密后:" +  new String(output)); 
    }
    
}
