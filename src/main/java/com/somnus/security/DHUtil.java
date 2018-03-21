package com.somnus.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/** DH安全编码组件 
 * @Title: DHUtil.java 
 * @Package com.somnus.cipher 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月6日 上午10:29:30 
 * @version V1.0 
 */
public class DHUtil {
	public static final String ALGORITHM = "DH";  
	  
    /** 
     * 默认密钥字节数 
     *  
     * <pre> 
     * DH 
     * Default Keysize 1024   
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive). 
     * </pre> 
     */  
    private static final int KEY_SIZE = 1024;  
  
    /** 
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。 
     */  
    public static final String SECRET_ALGORITHM = "DES";  
    private static final String PUBLIC_KEY = "DHPublicKey";  
    private static final String PRIVATE_KEY = "DHPrivateKey";  
  
    /** 
     * 初始化甲方密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);  
        keyPairGenerator.initialize(KEY_SIZE);  
        KeyPair keyPair = keyPairGenerator.generateKeyPair();  
        // 甲方公钥  
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();  
        // 甲方私钥  
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
  
    /** 
     * 初始化乙方密钥 
     *  
     * @param key 
     *            甲方公钥 
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey(String key) throws Exception {  
        // 解析甲方公钥  
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);  
        // 由甲方公钥构建乙方密钥  
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();  
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());  
        keyPairGenerator.initialize(dhParamSpec);  
        KeyPair keyPair = keyPairGenerator.generateKeyPair();  
        // 乙方公钥  
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();  
        // 乙方私钥  
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
  
    /** 
     * 加密<br> 
     *  
     * @param data 
     *            待加密数据 
     * @param publicKey 
     *            甲方公钥 
     * @param privateKey 
     *            乙方私钥 
     * @return 
     * @throws Exception 
     */  
    public static String encrypt(String data, String publicKey, String privateKey) throws Exception {  
        // 生成本地密钥  
        SecretKey secretKey = getSecretKey(publicKey, privateKey);  
        // 数据加密  
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] buff = cipher.doFinal(data.getBytes());
        System.out.println(Arrays.toString(buff));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Hex.encodeHexString(buff);
    }  
  
    /** 
     * 解密<br> 
     *  
     * @param data 
     *            待解密数据 
     * @param publicKey 
     *            乙方公钥 
     * @param privateKey 
     *            乙方私钥 
     * @return 
     * @throws Exception 
     */  
    public static String decrypt(String data, String publicKey,String privateKey) throws Exception {  
        // 生成本地密钥  
        SecretKey secretKey = getSecretKey(publicKey, privateKey);  
        // 数据解密  
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, secretKey);  
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        System.out.println(Arrays.toString(buff));
        return new String(buff);
    }  
  
    /** 
     * 构建密钥 
     *  
     * @param publicKey 
     *            公钥 
     * @param privateKey 
     *            私钥 
     * @return 
     * @throws Exception 
     */  
    private static SecretKey getSecretKey(String publicKey, String privateKey)throws Exception {  
        // 初始化公钥  
        byte[] pubKeyBytes = Base64.decodeBase64(publicKey);  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);  
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);  
        // 初始化私钥  
        byte[] priKeyBytes = Base64.decodeBase64(privateKey); 
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);  
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
        KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());  
        keyAgree.init(priKey);  
        keyAgree.doPhase(pubKey, true);  
        // 生成本地密钥  
        SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);  
        return secretKey;  
    }  
  
    /** 
     * 取得私钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
        return Base64.encodeBase64String(key.getEncoded());
    }  
  
    /** 
     * 取得公钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
        return Base64.encodeBase64String(key.getEncoded());  
    }
    
    public static void main(String[] args) throws Exception {
    	// 生成甲方密钥对儿  
        Map<String, Object> aKeyMap = initKey();  
        String aPublicKey = getPublicKey(aKeyMap);  
        String aPrivateKey = getPrivateKey(aKeyMap);  
        System.out.println("甲方公钥:\r" + aPublicKey);  
        System.out.println("甲方私钥:\r" + aPrivateKey);  
          
        // 由甲方公钥产生本地密钥对儿  
        Map<String, Object> bKeyMap = initKey(aPublicKey);  
        String bPublicKey = getPublicKey(bKeyMap);  
        String bPrivateKey = getPrivateKey(bKeyMap);  
        System.out.println("乙方公钥:\r" + bPublicKey);  
        System.out.println("乙方私钥:\r" + bPrivateKey);  
          
        String data = "Somnus ";  
        System.out.println("原文: " + data);  
        // 由甲方公钥，乙方私钥构建密文  
        String encryptData = encrypt(data, aPublicKey,bPrivateKey);  
        System.out.println("加密后: " + encryptData);
        // 由乙方公钥，甲方私钥解密  
        String decryptData = decrypt(encryptData, bPublicKey, aPrivateKey);  
        System.out.println("解密后: " + decryptData);  
  
        System.out.println(" ===============反过来加密解密================== ");  
        System.out.println("原文: " + data);  
        // 由乙方公钥，甲方私钥构建密文  
        String encryptData2 = encrypt(data, bPublicKey,aPrivateKey);
        System.out.println("加密后: " + encryptData2);
        // 由甲方公钥，乙方私钥解密  
        String decryptData2 = decrypt(encryptData2, aPublicKey, bPrivateKey);  
        System.out.println("解密后: " + decryptData2);  
	}

}
