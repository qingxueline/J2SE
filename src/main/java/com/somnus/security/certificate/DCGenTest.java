package com.somnus.security.certificate;

import org.junit.Test;

import java.util.Date;

/**
 * DCGenTest
 *
 * @author lyl
 * @version 2020/3/19 0019 15:32:24
 */
public class DCGenTest {
    private static String path = "D:/Cert/";

    /***
     * 创建新证书
     */
    @Test
    public void testGenerateCert() {
//        KeyStoreInfo certInfo = new KeyStoreInfo("中国电信A", "123", "456", "中国电信广州研究院", "广州研究院",
//                "中国电信", "广州天河区", "广东", "中国", new Date(), 365L, path + "RootDXCert.pfx");
//        DigitalCertificateGenerator.generatePFX(certInfo);

//        KeyStoreInfo wuming = new KeyStoreInfo("wuming", "123", "456", "中国电信广州研究院", "广州研究院",
//                "中国电信", "广州天河区", "广东", "中国", new Date(), 365L, path + "wuming.pfx");
//        DigitalCertificateGenerator.generatePFX(wuming);

        KeyStoreInfo keyStore = new KeyStoreInfo("smallcellkeypair", "8329XFE8&^1", "8329XFE8&^1", "CT SAMLL CEL", "SAMLL CEL",
                "GSTA", "TH", "GD", "CN", new Date(), 365L*500, path + "smallCellKeyPair.jks");
        DigitalCertificateGenerator.generateJKS(keyStore);

//        certInfo = new KeyStoreInfo("无名5", "789", "101", "7", "8", "9", "10",
//                "11", "12", new Date(), 365L, path + "wuming5.keystore");
//        DigitalCertificateGenerator.generateJKS(certInfo);

        System.out.println("testGenerateCert end");
    }

    /***
     * 将新的证书加入到证书库
     */
    @Test
    public void testAddNewCert() {
        KeyStoreInfo certInfo = new KeyStoreInfo("荆轲6", "123", "456", "1", "2",
                "3", "4", "5", "6", new Date(), 365L, path + "RootDXCert.pfx");
        DigitalCertificateGenerator.addNewCert2PFX(certInfo);

//        certInfo = new KeyStoreInfo("无名9", "789", "101", "7", "8", "9", "10",
//                "11", "12", new Date(), 365L, path + "CurrentTest.keystore");
//        DigitalCertificateGenerator.addNewCert2JKS(certInfo);
    }

    /***
     * 导出数字证书
     */
    @Test
    public void testExportCert() {
        DigitalCertificateGenerator.exportPFXPublicKeyCertificate(path + "RootDXCert.pfx", "123", "中国电信A", path + "RootDXCert.cer");
        DigitalCertificateGenerator.exportPFXPublicKeyCertificate(path + "wuming.pfx", "123", "wuming", path + "wuming.cer");
    }

    /***
     * 打印查看数字证书
     * @throws Exception
     */
    @Test
    public void testPrintCert() throws Exception {
        String certPath = path + "smallCellKeyPair.jks";
        String keyStorePass = "8329XFE8&^1";
        String certPass = "8329XFE8&^1";
        String keyStoreType = "jks";
        CertUtil.printCert(certPath, certPass, keyStoreType, keyStorePass);
    }

    /***
     * 导出公钥证书
     * @throws Exception
     */
    @Test
    public void testExportPFXPublicKeyCertificate() throws Exception {
        String storePathAndFileName = path + "RootDXCert.pfx";
        String keyStorePass = "123";
        String alias = "中国电信A";
        String exportPathAndFileName = path + "RootDXCert.cer";
        DigitalCertificateGenerator.exportPFXPublicKeyCertificate(storePathAndFileName, keyStorePass, alias, exportPathAndFileName);
    }


    /****
     * 颁发数字证书
     */
    @Test
    public void testSignCert() {
        SignedCertInfo signedCertInfo = new SignedCertInfo();

        String issuerAlias = "中国电信A";
        String keyStorePass = "123";
        String certPass = "456";
        //证书库路径
        String pathAndFileName = path + "RootDXCert.pfx";

        //新颁发的证书信息------------------------------------------------
        String cN = "中国电信广州研究院B";
        String oU = "广州研究院";
        String o = "中国电信";
        String l = "广州天河区";
        String sT = "广东";
        String c = "中国";
        String subjectAlias = "中国电信B";
        String subjectAliasPass = "456";
        int validityDays = 365;
        //颁发证书路径
        String subjectPath = path + "signedCert.cer";

        signedCertInfo.setIssuerAlias(issuerAlias);
        //作用是用来恢复根证书的秘钥，防止别人拿到根证书后可以用来根证书颁发证书
        signedCertInfo.setIssuerAliasPass(certPass);
        signedCertInfo.setKeyStorePass(keyStorePass);
        signedCertInfo.setCN(cN);
        signedCertInfo.setOU(oU);
        signedCertInfo.setO(o);
        signedCertInfo.setL(l);
        signedCertInfo.setST(sT);
        signedCertInfo.setC(c);
        signedCertInfo.setValidity(validityDays);

        signedCertInfo.setSubjectAlias(subjectAlias);
        signedCertInfo.setSubjectAliasPass(subjectAliasPass);
        signedCertInfo.setKeyStorePath(pathAndFileName);
        signedCertInfo.setSubjectPath(subjectPath);

        System.out.println(signedCertInfo);

        //颁发证书
        DigitalCertificateGenerator.signCertPFXForSubject(signedCertInfo);
    }

    @Test
    public void genCSR2() {
        DigitalCertificateGenerator.genCSR2();
    }

    @Test
    public void getPublicKeyByP10() {
        DigitalCertificateGenerator.getPublicKeyByP10();
    }

    @Test
    public void cer() {
        DigitalCertificateGenerator.cer();
    }
}
