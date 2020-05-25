package com.somnus.security.certificate;

/**
 * CertUtilTest
 *
 * @author lyl
 * @version 2020/3/19 0019 15:32:59
 */

import org.junit.Test;

public class CertUtilTest {

    private static String path = "D:/Cert/";

    /**
     * 校验数字证书
     */
    @Test
    public void testCertVerifing() {
        //同一机构颁发的证书可以完成签名验证
        CertHelper.verifySign(path + "RootDXCert.cer", path + "signedCert.cer");
        //不同一机构颁发的证书可以完成签名验证失败
        CertHelper.verifySign(path + "RootDXCert.cer", path + "wuming.cer");
        System.out.println("test passed!");
    }

    /**
     * 校验数字证书有效期
     */
    @Test
    public void testCertValidityDays() {
        CertHelper.verifyValidityDays(path + "signedCert.cer");
        System.out.println("test passed!");
    }

    /***
     * 获取数字证书别名列表，包含子证书
     */
    @Test
    public void testGetAllAliasesInfo() {
        System.out.println(CertHelper.allAliasesInPFX(path + "RootDXCert.pfx", "123"));
    }

    /***
     * 获取证书公钥
     */
    @Test
    public void testPublicKeyInCert() {
        System.out.println("cer:" + CertHelper.publicKeyInCert(path + "signedCert.cer"));
        System.out.println("pfx:" + CertHelper.publicKeyInPFX(path + "RootDXCert.pfx", "123", "中国电信A"));
    }

    /***
     * 获取证书私钥。私钥只有PFX和JKS的证书保存私钥
     */
    @Test
    public void testPrivateKey() {
        System.out.println(CertHelper.privateKeyInPFX(path + "RootDXCert.pfx", "123", "中国电信A", "456"));
    }

    /**
     * 测试使用秘钥库进行编码解码
     */
    @Test
    public void testKeyStoreEncodeAndDecode() {
        String msg = "你好啊，这是测试数据。";

        byte[] data = CertHelper.encodeByPFXPublicKey(path + "RootDXCert.pfx", "123", "中国电信A", msg.getBytes());
        System.out.println(new String(data));
        data = CertHelper.decodeByPFXPrivateKey(path + "RootDXCert.pfx", "123", "中国电信A", "456", data);
        System.out.println(new String(data));

        System.out.println("==============");

        data = CertHelper.encodeByPFXPublicKey(path + "RootDXCert.pfx", "123", "中国电信A", msg.getBytes());
        System.out.println(new String(data));
        data = CertHelper.decodeByPFXPrivateKey(path + "RootDXCert.pfx", "123", "中国电信A", "456", data);
        System.out.println(new String(data));
    }

    /**
     * 测试数字证书公钥编码，密钥库私钥解码
     */
    @Test
    public void testCerFileEncodeAndDecode() {
        String msg = "你好啊，这是测试数据。";
        //数字证书公钥编码，密钥库私钥解码
        byte[] encodeBytes = CertHelper.encodeByCert(path + "signedCert.cer", msg.getBytes());
        System.out.println(new String(encodeBytes));
        byte[] decodeBytes = CertHelper.decodeByPFXPrivateKey(path + "RootDXCert.pfx", "123", "中国电信A", "456", encodeBytes);
        System.out.println(new String(decodeBytes));

        System.out.println("=============================");

        //密钥库私钥编码，数字证书公钥解码，
        encodeBytes = CertHelper.encodeByPFXPrivateKey(path + "RootDXCert.pfx", "123", "中国电信A", "456", msg.getBytes());
        System.out.println(new String(encodeBytes));
        decodeBytes = CertHelper.decodeByCert(path + "signedCert.cer", encodeBytes);
        System.out.println(new String(decodeBytes));
    }
}
