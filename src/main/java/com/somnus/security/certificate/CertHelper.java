package com.somnus.security.certificate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.security.pkcs10.PKCS10;
import sun.security.x509.X500Name;

import java.security.PublicKey;
import java.util.Base64;

/**
 * @author lyl
 * @version 2020/5/12 0012 23:04:26
 */
@Slf4j
public class CertHelper {

    /**
     * 通过P10文件提取公钥
     *
     * @param p10str p10文件
     * @return
     */
    public static String getPublicKeyByP10(String p10str) {
        PKCS10 pkcs10 = getP10Request(p10str);
        PublicKey publickey = pkcs10.getSubjectPublicKeyInfo();
        String publicKeyStr = Base64.getMimeEncoder().encodeToString(publickey.getEncoded());
        publicKeyStr = wrapPublicKey(publicKeyStr);
        log.debug(String.format("publicKeyStr：%s%s", System.getProperty("line.separator"), publicKeyStr));
        return publicKeyStr;
    }

    /**
     * 通过P10获取持有者信息
     *
     * @param p10str p10文件
     * @return
     */
    public static String getSubjectByP10(String p10str) {
        PKCS10 pkcs10 = getP10Request(p10str);
        X500Name x500name = pkcs10.getSubjectName();
        return x500name.getName();
    }

    public static PKCS10 getP10Request(String p10str) {
        try {
            return new PKCS10(Base64.getMimeDecoder().decode(removeTagP10(p10str)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装公钥信息
     *
     * @param publicKeyStr 公钥
     * @return .
     */
    public static String wrapPublicKey(String publicKeyStr) {
        String begin = "-----BEGIN PUBLIC KEY-----";
        String end = "-----END PUBLIC KEY-----";
        publicKeyStr = String.format("%s%s%s%s%s", begin, System.getProperty("line.separator"), publicKeyStr.trim(), System.getProperty("line.separator"), end);
        log.debug(String.format("PublicKeyStr：%s%s", System.getProperty("line.separator"), publicKeyStr));
        return publicKeyStr;
    }

    /**
     * 封装私钥钥信息
     *
     * @param privateKeyStr 私钥
     * @return .
     */
    public static String wrapPrivateKey(String privateKeyStr) {
        String begin = "-----BEGIN RSA PRIVATE KEY-----";
        String end = "-----END RSA PRIVATE KEY-----";
        privateKeyStr = String.format("%s%s%s%s%s", begin, System.getProperty("line.separator"), privateKeyStr.trim(), System.getProperty("line.separator"), end);
        log.debug(String.format("PrivateKeyStr：%s%s", System.getProperty("line.separator"), privateKeyStr));
        return privateKeyStr;
    }

    /**
     * 封装P10成为请求文件格式
     *
     * @param p10str P10
     * @return .
     */
    public static String wrapP10(String p10str) {
        String begin = "-----BEGIN CERTIFICATE REQUEST-----";
        String end = "-----END CERTIFICATE REQUEST-----";
        p10str = String.format("%s%s%s%s%s", begin, System.getProperty("line.separator"), p10str.trim(), System.getProperty("line.separator"), end);
        log.debug(String.format("p10str：%s%s", System.getProperty("line.separator"), p10str));
        return p10str;
    }

    /**
     * 移除P10请求的开始和结束标记
     *
     * @param p10str P10文件
     * @return .
     */
    public static String removeTagP10(String p10str) {
        String begin = "-----BEGIN CERTIFICATE REQUEST-----";
        String end = "-----END CERTIFICATE REQUEST-----";
        if (StringUtils.startsWith(p10str, begin)) {
            p10str = StringUtils.substring(p10str, begin.length(), p10str.length() - end.length());
        }
        log.debug(String.format("p10str：%s%s", System.getProperty("line.separator"), p10str));
        return p10str.trim();
    }

    /**
     * 移除公钥开始和结束标记
     *
     * @param publicKeyStr 公钥文件
     * @return .
     */
    public static String removeTagPublicKey(String publicKeyStr) {
        String begin = "-----BEGIN PUBLIC KEY-----";
        String end = "-----END PUBLIC KEY-----";
        if (StringUtils.startsWith(publicKeyStr, begin)) {
            publicKeyStr = StringUtils.substring(publicKeyStr, begin.length(), publicKeyStr.length() - end.length());
        }
        log.debug(String.format("PublicKeyStr：%s%s", System.getProperty("line.separator"), publicKeyStr));
        return publicKeyStr.trim();
    }

    /**
     * 移除公钥开始和结束标记
     *
     * @param privateStr 公钥文件
     * @return .
     */
    public static String removeTagPrivateKeyStr(String privateStr) {
        String begin = "-----BEGIN RSA PRIVATE KEY-----";
        String end = "-----END RSA PRIVATE KEY-----";
        if (StringUtils.startsWith(privateStr, begin)) {
            privateStr = StringUtils.substring(privateStr, begin.length(), privateStr.length() - end.length());
        }
        log.debug(String.format("PrivateKeyStr：%s%s", System.getProperty("line.separator"), privateStr));
        return privateStr.trim();
    }
}
