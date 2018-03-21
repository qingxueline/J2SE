package com.somnus.security.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *单向加密算法支持MD5、SHA、MAC，BASE64
 * Created by lyl on 2017/6/29 0029.
 */
public class Coder {

    public static final String KEY_MD5 = "MD5";
    public static final String KEY_SHA = "SHA";
    /**
     * MAC算法可选以下多种算法
     * <p>
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * md5加密
     *
     * @param str
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String md5(String str) throws Exception {
        return encrypt(str, KEY_MD5);
    }

    /**
     * sha加密
     *
     * @param str
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String sha(String str) throws Exception {
        return encrypt(str, KEY_SHA);
    }

    /**
     * md5或者sha-1加密
     *
     * @param str           要加密的内容
     * @param algorithmName 加密算法名称：md5或者sha-1，不区分大小写
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    private static String encrypt(String str, String algorithmName) throws Exception {
        MessageDigest m = MessageDigest.getInstance(algorithmName);
        m.update(str.getBytes(/*"UTF8"*/));
        byte s[] = m.digest();
        return Hex.encodeHexString(s);
    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     * @throws Exception
     */
    public static String encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte s[] = mac.doFinal(data);
        return Hex.encodeHexString(s);
    }

    /**
     * BASE64解密
     *
     * @param key 。公钥或者私钥
     * @return 。
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key 。公钥或者私钥
     * @return 。
     * @throws Exception 。
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
