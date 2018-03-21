package com.somnus.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**对称加密算法DES&AES
 * @description: TODO 
 * @author Somnus
 * date 2015年4月8日 下午2:10:44  
 */
public class DESUtil {
    /** 
     * ALGORITHM 算法 <br> 
     * 可替换为以下任意一种算法，同时key值的size相应改变。 
     *  
     * <pre> 
     * DES                  key size must be equal to 56 
     * DESede(TripleDES)    key size must be equal to 112 or 168 
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
     * RC2                  key size must be between 40 and 1024 bits 
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
     * </pre> 
     *  
     * 在Key keyGenerator(byte[] key)方法中使用下述代码 
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换 
     * <code> 
     * DESKeySpec desKey = new DESKeySpec(key); 
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); 
     * SecretKey secretKey = keyFactory.generateSecret(desKey); 
     * </code> 
     */
    public static final String ALGORITHM = "DES";
    //算法名称/加密模式/填充方式 
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

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
    private static SecretKey keyGenerator(byte[] key) throws Exception {
        DESKeySpec desKey = new DESKeySpec(key);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        
        /*当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码 */
        /*SecretKey secretKey = new SecretKeySpec(key,KEY_ALGORITHM);*/  
        return securekey;
    }

    /** 
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据 
     */
    public static String encrypt(String data, String keyStr) throws Exception {
        Key deskey = keyGenerator(Base64.decodeBase64(keyStr));
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] buff = cipher.doFinal(data.getBytes());
        System.out.println(Arrays.toString(buff));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Base64.encodeBase64String(buff);
    }

    /** 
     * 解密数据 
     * @param data 待解密数据 
     * @param key 密钥 
     * @return 解密后的数据 
     */
    public static String decrypt(String data, String keyStr) throws Exception {
        Key deskey = keyGenerator(Base64.decodeBase64(keyStr));
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Base64.decodeBase64(data));
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
       KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);  
       kg.init(secureRandom);  
       SecretKey secretKey = kg.generateKey();  
       return Base64.encodeBase64String(secretKey.getEncoded()); 
   }
   
   public static void main(String[] args) throws Exception {
        String key = initKey();
        System.out.println(key+"size:"+Base64.decodeBase64(key).length);
        
        String source = "Somnus";
        System.out.println("原文: " + source);
        
        String encryptData = encrypt(source, key);
        System.out.println("加密后: " + encryptData);
        
        String decryptData = decrypt(encryptData, key);
        System.out.println("解密后: " + decryptData);
    }
}
