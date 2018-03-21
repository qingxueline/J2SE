package com.somnus.security;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 非对称加密算法RSA,方式一
 *
 * @author Somnus
 * @version V1.0
 * @Title: RsaEncrypt.java
 * @Description: TODO
 * @date 2015年6月5日 下午2:02:44
 */
public class RSAUtil {
    public static final String ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /*公钥key*/
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /*私钥key*/
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /*公钥文件*/
    private static final String PUBLIC_KEY_PATH = "public.cer";
    /*私钥文件*/
    private static final String PRIVATE_KEY_PATH = "private.key";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data 加密数据
     * @return
     * @throws Exception
     */
    public static String sign(String data) throws Exception {
        // 解密由base64编码的私钥  
        byte[] keyBytes = Base64.decodeBase64(getPrivateKey());
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(Hex.decodeHex(data.toCharArray()));
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data 加密数据
     * @param sign 数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(String data, String sign) throws Exception {
        // 解密由base64编码的公钥  
        byte[] keyBytes = Base64.decodeBase64(getPublicKey());
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(Hex.decodeHex(data.toCharArray()));
        // 验证签名是否正常  
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data) throws Exception {
        // 对密钥解密  
        byte[] keyBytes = Base64.decodeBase64(getPrivateKey());
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //初始化Cipher对象，设置为解密模式
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        System.out.println(Arrays.toString(buff));
        return new String(buff);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data) throws Exception {
        // 对密钥解密  
        byte[] keyBytes = Base64.decodeBase64(getPublicKey());
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        //初始化Cipher对象，设置为解密模式
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        System.out.println(Arrays.toString(buff));
        return new String(buff);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data) throws Exception {
        // 对公钥解密  
        byte[] keyBytes = Base64.decodeBase64(getPublicKey());
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] buff = cipher.doFinal(data.getBytes());
        System.out.println(Arrays.toString(buff));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Hex.encodeHexString(buff);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data) throws Exception {
        // 对密钥解密  
        byte[] keyBytes = Base64.decodeBase64(getPrivateKey());
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] buff = cipher.doFinal(data.getBytes());
        System.out.println(Arrays.toString(buff));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Hex.encodeHexString(buff);
    }

    /**
     * 取得私钥
     *
     * @return
     * @throws Exception
     */
    public static String getPrivateKey() throws Exception {
        Key key = (Key) initKey().get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @return
     * @throws Exception
     */
    public static String getPublicKey() throws Exception {
        Key key = (Key) initKey().get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        InputStream in1 = RSAUtil.class.getClassLoader().getResourceAsStream(PUBLIC_KEY_PATH);
        InputStream in2 = RSAUtil.class.getClassLoader().getResourceAsStream(PRIVATE_KEY_PATH);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            /*生成公钥*/
            byte[] encodedpubkey = new byte[in1.available()];
            in1.read(encodedpubkey);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedpubkey);
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
            /*生成私钥*/
            byte[] encodedprikey = new byte[in2.available()];
            in2.read(encodedprikey);
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(encodedprikey);
            PrivateKey privateKey = keyFactory.generatePrivate(priKeySpec);
            /*封装进map*/
            Map<String, Object> keyMap = new HashMap<String, Object>();
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            in1.close();
            in2.close();
        }
    }
    public static void main(String[] args) throws Exception {
        String publicKey = getPublicKey();
        String privateKey = getPrivateKey();
        System.out.println("公钥:\r" + publicKey);
        System.out.println("私钥:\r" + privateKey);

        System.out.println("公钥加密————————————————————私钥解密");
        String data = "Somnus";
        System.out.println("原文: " + data);
        String encryptData = encryptByPublicKey(data);
        System.out.println("加密后: " + encryptData);
        String decryptData = decryptByPrivateKey(encryptData);
        System.out.println("解密后: " + decryptData);

        System.out.println("私钥加密————————————————————公钥解密");
        System.out.println("原文: " + data);
        String encryptData2 = encryptByPrivateKey(data);
        System.out.println("加密后: " + encryptData2);
        String decryptData2 = decryptByPublicKey(encryptData2);
        System.out.println("解密后: " + decryptData2);

        System.out.println("私钥签名——公钥验证签名");
        // 产生签名  
        String sign = sign(encryptData2);
        System.out.println("签名:\r" + sign);
        // 验证签名  
        boolean status = verify(encryptData2, sign);
        System.out.println("状态:" + status);
    }
}
