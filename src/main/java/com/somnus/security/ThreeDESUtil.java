package com.somnus.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** 
 * @description: TODO 
 * @author Somnus
 * date 2015年4月8日 下午2:31:36  
 */
public class ThreeDESUtil {
    // 算法名称 
    public static final String KEY_ALGORITHM = "desede";
    // 算法名称/加密模式/填充方式 
    public static final String CIPHER_ALGORITHM = "desede/CBC/NoPadding";
    
    /** 
     *   
     * 生成密钥key对象 
     * @param KeyStr 密钥字符串 
     * @return 密钥对象 
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     * @throws InvalidKeySpecException   
     * @throws Exception 
     */
    private static Key keyGenerator(byte[] key) throws Exception {
        DESedeKeySpec KeySpec = new DESedeKeySpec(key);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }

    /** 
     * CBC加密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */
    public static String encrypt(String key, byte[] keyiv, String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider()); 
        Key deskey = keyGenerator(Base64.decodeBase64(key));
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] buff = cipher.doFinal(data.getBytes());
        System.out.println(Arrays.toString(buff));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Hex.encodeHexString(buff);
    }


    /** 
     * CBC解密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */
    public static String decrypt(String key, byte[] keyiv, String data) throws Exception {
        Key deskey = keyGenerator(Base64.decodeBase64(key));
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        System.out.println(Arrays.toString(buff));
        return new String(buff);
    }
    
    /** 
     * 生成密钥 
     * @return 
     * @throws Exception 
     */  
    public static String initKey() throws Exception {  
        return initKey(null);  
    }  
    /** 
    * 生成密钥 
    * @param seed 
    * @return 
    * @throws Exception 
    */  
   public static String initKey(String seed) throws Exception {  
       SecureRandom secureRandom = null;  
       if (seed != null) {  
           secureRandom = new SecureRandom(Base64.decodeBase64(seed));  
       } else {  
           secureRandom = new SecureRandom();  
       }  
       KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);  
       kg.init(secureRandom);  
       SecretKey secretKey = kg.generateKey();  
       return Base64.encodeBase64String(secretKey.getEncoded()); 
   }

    public static void main(String[] args) throws Exception {
        String key = initKey();
        System.out.println(key+"size:"+Base64.decodeBase64(key).length);
        byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
        
        String source = "Somnusss";
        System.out.println("原文: " + source);


        String encryptData = encrypt(key, keyiv, source);
        System.out.println("加密后: " + encryptData);
        
        String decryptData = decrypt(key, keyiv, encryptData);
        System.out.println("解密后: " + decryptData);
    }
}