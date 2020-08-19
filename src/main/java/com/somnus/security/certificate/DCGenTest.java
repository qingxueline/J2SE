package com.somnus.security.certificate;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import sun.security.x509.X509CertImpl;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
                "GSTA", "TH", "GD", "CN", new Date(), 365L * 500, path + "smallCellKeyPair.jks");
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
        CertHelper.printCert(certPath, certPass, keyStoreType, keyStorePass);
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

    @Test
    public void getPrivateKey() {
        String key = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCukiPx8ATXBR6MT2G/1XLNVDYP\n" +
                "81ce7XcV/rkduwnmUbi7fwgXOnndUkUd2LcIg/Z3Ey7iTbwNY/Yjxp5Eq8hjZI1Gj0Yn+82iioJ6\n" +
                "B4a09zLSXQEjgB/NamnOE3K9s8rST/+DMCuAvLX9P4qVocrjy8y6Dq+1ycZuYNnQWsQoYf09Qn2v\n" +
                "pjpURzYNxGCNQJ0HQMts9RDmoiyCL3MqiSzOo9v0km8bUl5LjpZMDMIXf8hRmHkGg/LD7XYE7YX9\n" +
                "gSTorropBVXMc+0EwizUH0t+CUs8EjkEhgMWMlyX4Y+9TF82rJi50p0CcZFnI1KfV7pprQcJmLql\n" +
                "00HSuNqs/V+LAgMBAAECggEAacaHHSuP4zFo5x+aXQBahWuHLncFzOmaBKvJQuh4ASR3SLSaFGkM\n" +
                "OjyFQKZ1kSVUi6YF/H2ZeoudMuRANd/YldDxgVWC7us2GEzQ7lvtFW3LT4enDvmtS2w1G/bQyX1N\n" +
                "Yc2zRqxDT8gIhaCgujXEl+5+6CXJ9d6C65KnqAW3YberH+X42jGciRyMiIG4+Kgpk7tbLBVfe+V0\n" +
                "9cVY3Cm/FcabaS0OwbqWL1u+Ee1XA3MamsG++yvT8Mi8uJ7NObEWvwJH3N/N19MgNcLVihKOiz8W\n" +
                "yGtcs6Y+/cCuGzAFir8BmlXL5Qjnr9ywJFEKamYxbASsg3pE7qKDULnV1kk+8QKBgQD08YQsRIRj\n" +
                "KvObwvb+mrBtBjypSsIhSPQaLn4oFv2vPRT3c4SJ36HBKSA/vxYTEStrm+pE09fzeBRxJvIvzWmM\n" +
                "0dRiaxdRtyE6fsgwOkB7cAuttDcUrxKoew5s0TqDZ5uYBTihydg90XGJUYwVFSNq2oFe74wI3H5W\n" +
                "Akho5JkhtwKBgQC2c2wn7Ru8tlXmaJJYw9ErTGyQjpP5oECqN5HkwcH1GB3ScRbRcxJVgvcGiS0+\n" +
                "Kbv5SfqgDxFYgJcwJ5f+UM0HPkmVARgR4beTYT/Im9Rdch+xT/zWl62kJoISpAutVKSDn2mVeGLH\n" +
                "UG6X1xNZKUsEcMRgX9ZBbUafYvdYZQ2gzQKBgQCbZaY/F3EOIS/QQA7HVIhdgrl1hfJHf6st+RUc\n" +
                "hgmH5a6pluYdOn26uEa3apmsmxX6/T3RUL0Ov73oU5cO8LjoGC8wCVUQCKjSHaEPx8UlCt694zmW\n" +
                "q8OglAGA0/TVbSPvfFCDhDTmczwerFe9AF4p6lptSniDHrAgDO63bWAhXQKBgDzF+bLa8jBL9BUJ\n" +
                "pCXZJCVuEYOdWoD99kw8smYmfDrty5E7hkRCNksxkqMksmNQd4K0N711B2wZeULbDW/y7jiJcO0n\n" +
                "LiNWslVvQVXLQlPWHeRsJmY8o+RXx1/6MMGNvjbxtLPLI+wMSKpfQ9yhOpHCFkeb5Lxqb+nLh2R0\n" +
                "VLPJAoGBAOviYyVyL9rsnI8JPQ8u/ziVOgpnQIjYm9qm5iWuNFFuBW04xfSpMNrzVHqZIw2pwcLz\n" +
                "c8jH0BTn+rVUgE2z/OowHeVTBnH+EQf9T5SlXJiMipRk2aSFvKPVJW3KjalOMJx9xTvFcr1P/6dW\n" +
                "o/VmFAJARU9ciVUd73E4/dVRLhJ8\n" +
                "-----END RSA PRIVATE KEY-----";
        try {
            PrivateKey privateKey = CertHelper.getPrivateKey(key);
            System.out.println(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void tset() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, NoSuchProviderException, SignatureException, InvalidAlgorithmParameterException, CertPathValidatorException {
        //小站证书
        String cerStr = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDoTCCAomgAwIBAgIUUYV71Bx6hjw9Wh2mv2j+o/yquzYwDQYJKoZIhvcNAQEL\n" +
                "BQAwWTELMAkGA1UEBhMCQ04xKDAmBgNVBAoMH0NoaW5hIFRlbGVjb20gR3Vhbmdk\n" +
                "b25nIEFjYWRlbXkxIDAeBgNVBAMMF0d1YW5nZG9uZyBBY2FkZW15IFN1YkNBMB4X\n" +
                "DTIwMDgwNTE0NTgyM1oXDTMwMDgwNTE0NTgyM1owWjELMAkGA1UEBhMCQ04xCzAJ\n" +
                "BgNVBAgMAkdEMQ0wCwYDVQQKDARnc3RhMRQwEgYDVQQLDAtTTlRlc3QwMDAwMTEZ\n" +
                "MBcGA1UEAwwQZ3N0YV9TTlRlc3QwMDAwMTCCASIwDQYJKoZIhvcNAQEBBQADggEP\n" +
                "ADCCAQoCggEBANqXpQW5zS/XSMzQGjbQ5o+tPC06FLP0HEdkoC9pe36jn1uvWoQ2\n" +
                "wzO936zx4efK+COfMvSEKMnVrJXavSvNRXCoBEdi2L6EPpSwuDynA0tmzoA7s1KN\n" +
                "5Sk3naVOm0+ze040iVPwIT5EtAbGCBg9sHxUJTZuZ3m7Kkil+ZAVZVVOZpQ2yDBf\n" +
                "8+/48fmT9zupluhBBoVsKX5ZppLpCuC+bZoatmgIAFvF259L/4A6q7GVsWbrNSsn\n" +
                "ewYEVJ0ca99txt3gsIo6FlA5AV+eCNwZcycJX1yJlOicCm9sT5Lu9BqnN/qZHdQE\n" +
                "k+IHxrY1afduemvsHERBtVO+cQI+X5W7n38CAwEAAaNgMF4wHQYDVR0OBBYEFK3J\n" +
                "tpqSvdmkqjlZfULz1wucvajyMA4GA1UdDwEB/wQEAwIGwDAMBgNVHRMBAf8EAjAA\n" +
                "MB8GA1UdIwQYMBaAFBAumQm2KLxVkRg5SM0P4iR++EoeMA0GCSqGSIb3DQEBCwUA\n" +
                "A4IBAQC9xLR+B22+mH+HB5ilcRtt/Sr5+8o7Cbdcyx+lwEwewcbar0y1I7h6sk9d\n" +
                "sjfZhwvkxh+poJdA/IcpZkFG/tGCXczDzJqIPUu6tZFQlB7VD/GWOb08uUGmJbPK\n" +
                "9U1mwwdudxvl+i6MgxEQv3UoMNjIullkkR3EByLxRAVv3EERTPwO5LbbPAp38Gz8\n" +
                "67UKTQZulB1Htq1rSkKlZo5gkeepUG8tNwApZjYoVJfPi/BPStIypiHKXPNSV7Et\n" +
                "UGq7OOtLh8e5hn9T68SQdmnobwiH94k6TRx+OkP7vnqbqWny6E++DlMRmv3bZ8yA\n" +
                "VnM+qjRm7gSAtooEYeL7skWj9oE5\n" +
                "-----END CERTIFICATE-----";

        String rootCaStr = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDpzCCAo+gAwIBAgIUBx2rWGwhULRX1BxPfHf7rTcH7gYwDQYJKoZIhvcNAQEL\n" +
                "BQAwWjELMAkGA1UEBhMCQ04xKDAmBgNVBAoMH0NoaW5hIFRlbGVjb20gR3Vhbmdk\n" +
                "b25nIEFjYWRlbXkxITAfBgNVBAMMGEd1YW5nZG9uZyBBY2FkZW15IFJvb3RDQTAg\n" +
                "Fw0yMDAzMTExMTI3NTFaGA8yMDUwMDMxMTExMjc1MVowWjELMAkGA1UEBhMCQ04x\n" +
                "KDAmBgNVBAoMH0NoaW5hIFRlbGVjb20gR3Vhbmdkb25nIEFjYWRlbXkxITAfBgNV\n" +
                "BAMMGEd1YW5nZG9uZyBBY2FkZW15IFJvb3RDQTCCASIwDQYJKoZIhvcNAQEBBQAD\n" +
                "ggEPADCCAQoCggEBAOvAgjoi0XamGfkn55NDYh3QFcQY/WpnqOcB8aFGaMNeM0qe\n" +
                "S7+qMyuhdEfSMc2PRhld5fDHUIeC2yhqEIREVyU3b/1L6TcNWhPXT2mO2KtAPjxH\n" +
                "/fOaw6FZ6s0C4kuA5pxEeZMbOq4XWrUdReMYf/Riqd+kH8k+Fjnkn+RKdny9JqLS\n" +
                "OEBL7otNkTgbMoHkoF/MQeEGOBkQHWvi6ei3cG4KUtcr1o/yyhrFg5+16BuaOJTJ\n" +
                "mThh6OD6qKAZEPzPPtRyFAHffKv9/OwAf4t3XFgLDqQbzfARoMo4j2n2hE/W0wZy\n" +
                "7QnqZEGJ+PgpVSnEqWPTZXwln+5j7UQRtwKKcBkCAwEAAaNjMGEwHQYDVR0OBBYE\n" +
                "FAtm4AdR4MYJ5zpV7tOv4uFRLEhbMA4GA1UdDwEB/wQEAwIBBjAPBgNVHRMBAf8E\n" +
                "BTADAQH/MB8GA1UdIwQYMBaAFAtm4AdR4MYJ5zpV7tOv4uFRLEhbMA0GCSqGSIb3\n" +
                "DQEBCwUAA4IBAQDi3+73YqT3B6VVCV7bxYaCUGEfYHJDlWDyFe5ygju4kr6lyzis\n" +
                "FGXW7sY2OHAnUjS2UlLy/JIiWLz0WkQoVM22Pd5yoqhdG8oVSKJaBWuDjkzd7+LU\n" +
                "eoAayZ4fsrhQGcuwT6qhhieGbRWrodCZaVjVsrTIdKmSHtPCNVhALc3Ca2ke+sow\n" +
                "F6uL0U2tcixGtXeHN3fkxZQoYMFCCUqtuDQgyWOg9pUsrt+nhgL1+c/FQNKMsJFL\n" +
                "9Eiuic/zkoZjLQROVomHSdVvc2eQBPAD38pYXmqlMLbhUrFsQEP0lxzT5aJkuWqD\n" +
                "TFrRaZ9ygrxhMYOXN8MDliHw+r4GWv4Uw0fi\n" +
                "-----END CERTIFICATE-----";
        String subCaStr = "-----BEGIN CERTIFICATE-----\n" +
                "MIIEEzCCAvugAwIBAgIUOpYHH04yR6Pq3Y7o7cKrGyk1uaswDQYJKoZIhvcNAQEL\n" +
                "BQAwWjELMAkGA1UEBhMCQ04xKDAmBgNVBAoMH0NoaW5hIFRlbGVjb20gR3Vhbmdk\n" +
                "b25nIEFjYWRlbXkxITAfBgNVBAMMGEd1YW5nZG9uZyBBY2FkZW15IFJvb3RDQTAe\n" +
                "Fw0yMDAzMTExNjAwMDBaFw00MDAzMTExNjAwMDBaMFkxCzAJBgNVBAYTAkNOMSgw\n" +
                "JgYDVQQKDB9DaGluYSBUZWxlY29tIEd1YW5nZG9uZyBBY2FkZW15MSAwHgYDVQQD\n" +
                "DBdHdWFuZ2RvbmcgQWNhZGVteSBTdWJDQTCCASIwDQYJKoZIhvcNAQEBBQADggEP\n" +
                "ADCCAQoCggEBANorJTu5/9gWmvB0qj4h1f9PizPlEOBHCq1lWPdCS2+Rg8FBUzdy\n" +
                "i73GSlF6hiW83gQ+Ut+B7d/XKuU7U70Jc/2Bt7Ac723BBlwwrt2qUpKM2Ims+usV\n" +
                "sR367c10KXFVlMh1z7hKBCyuIsQVtsgldR6h+CKqXOI+z2rJVOP2ULEcHsFMvJYa\n" +
                "aFhUWO99JvfwWmD18uhigOFOga3EV/wuNT2WPRjNYTdFQyH41sHqgo0fUX4V1lqg\n" +
                "iABHJhhr1BxU9V8uyZ0Y3i0uapnGuhUaUFTkEMNWWJ6e0sM/0bVrRzjbLzAP0vl3\n" +
                "661cxqo6scCl8ncVlRJFjEjyq91D18oDNqkCAwEAAaOB0TCBzjAdBgNVHQ4EFgQU\n" +
                "EC6ZCbYovFWRGDlIzQ/iJH74Sh4wDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB/wQF\n" +
                "MAMBAf8wawYDVR0gBGQwYjBgBgorBgEEAYGSSA0KMFIwUAYIKwYBBQUHAgEWRGh0\n" +
                "dHA6Ly93d3cuY25jYS5uZXQvY3Mva25vd2xlZGdlL3doaXRlcGFwZXIvY3BzL25l\n" +
                "dGNhdGVzdGNlcnRjcHMucGRmMB8GA1UdIwQYMBaAFAtm4AdR4MYJ5zpV7tOv4uFR\n" +
                "LEhbMA0GCSqGSIb3DQEBCwUAA4IBAQDrVFQnMlT3yjZeOM29+b56D8We2xCf1rw6\n" +
                "6rgpJfx1IEan7Oa+hLA1U8IYJpRb4yZRzfBh96nWZmxhToa30VBLKnPk2PEb6GoZ\n" +
                "N5o9F1D6FuBLM9jKGwG3/TZuz1/L+CbkvNdcN12/ViKpiUgfBOMGPAxdNl5L+4ve\n" +
                "ap5bmRuD8Bwfg+yWcqq9Z9TyV6cP6QBBr8SFE3Rs6zzcIBGVSVXPGgZvgxRvPXhA\n" +
                "APu0U+ivfOQFLaRSxUoVqF2dUo00nZ/71rLn44blnQ5rRZoOGYLARvvtHsH8JsJS\n" +
                "/mXpt94Q8bl5OKUge7/tznvdxM8OPB5nMq1zKl9T0QyaOThQH52A\n" +
                "-----END CERTIFICATE-----";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate rootCa = (X509Certificate) cf.generateCertificate(IOUtils.toInputStream(rootCaStr));
        X509Certificate subCa = (X509Certificate) cf.generateCertificate(IOUtils.toInputStream(subCaStr));
        X509Certificate cert = (X509Certificate) cf.generateCertificate(IOUtils.toInputStream(cerStr));
        subCa.verify(rootCa.getPublicKey());
        cert.verify(subCa.getPublicKey());
        System.out.println(rootCa.getSigAlgName());

        System.out.println("使用者信息：" + rootCa.getSubjectDN());
        System.out.println("颁发者信息：" + rootCa.getIssuerDN());
        System.out.println("颁发者信息：" + rootCa.getIssuerX500Principal().getName());

        CertPathValidator certPathValidator = CertPathValidator.getInstance(CertPathValidator.getDefaultType());
        TrustAnchor rootCaTrustAnchor = new TrustAnchor(rootCa, null);
        TrustAnchor subCaStrTrustAnchor = new TrustAnchor(subCa, null);
        Set<TrustAnchor> set = new HashSet<>();
        set.add(rootCaTrustAnchor);
        set.add(subCaStrTrustAnchor);
        PKIXParameters parameters = new PKIXParameters(set);
        CertificateFactory cf2 = CertificateFactory.getInstance("X.509");
        InputStream inputStream = IOUtils.toInputStream(cerStr);
        CertPath certPath = cf2.generateCertPath(inputStream);
        certPathValidator.validate(certPath, parameters);

    }
}
