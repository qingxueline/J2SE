package com.somnus.apache;

import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月12日 下午5:38:47 
 * @version V1.0
 */
public class CommonsCodec {
	
	@Test
	public void Base64(){
		String encode = Base64.encodeBase64String("Somnus罂粟花".getBytes(/*"UTF-8"*/));
		System.out.println("Base64 编码后：" + encode);
		
		byte[] decode = Base64.decodeBase64("U29tbnVz");
		String decodestr = new String(decode);  
        System.out.println("Base64 解码后："+decodestr); 
	}
	
	@Test
	public void Hex() throws DecoderException{
		byte[] buff = "Somnus罂粟花".getBytes(/*"utf-8"*/);
        System.out.println(Arrays.toString(buff));
        
        String byte2hex = Hex.encodeHexString(buff);
        System.out.println(byte2hex);
        
        byte[] hex2byte = Hex.decodeHex(byte2hex.toCharArray());
        System.out.println(Arrays.toString(hex2byte));
        System.out.println(new String(hex2byte));
	}
	
	@Test
	public void DigestUtils(){
		System.out.println(DigestUtils.md5Hex("admin"));
		/*System.out.println(DigestUtils.shaHex("admin"));*/
	}
}
