package com.somnus.security.certificate;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import sun.misc.BASE64Encoder;
import sun.security.pkcs10.PKCS10;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 数字证书中心
 *
 * @author lyl
 * @version 2020/3/19 0019 12:05:49
 */
public class DigitalCertificateGenerator {


    public static final String KEY_STORE_TYPE_JKS = "jks";
    public static final String KEY_STORE_TYPE_PKCS12 = "pkcs12";

    public static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    public static final String SECURE_RANDOM_PROVIDER = "SUN";
    public static final String SIGN_ALGORITHM_SHA256 = "SHA256withRSA";
    public static final String KEY_PAIR_ALGORITHM_RSA = "RSA";

    /**
     * @param signedCertInfo 颁发PFX格式数字证书
     */
    public static void signCertPFXForSubject(SignedCertInfo signedCertInfo) {
        signCert(signedCertInfo, KEY_STORE_TYPE_PKCS12);
    }

    /**
     * @param signedCertInfo 颁发JKS格式数字证书
     */
    public static void signCertJKSForSubject(SignedCertInfo signedCertInfo) {
        signCert(signedCertInfo, KEY_STORE_TYPE_JKS);
    }

    /**
     * @param signedCertInfo 数字证书信息
     * @param keyStoreType   秘钥库类型
     */
    private static void signCert(SignedCertInfo signedCertInfo, String keyStoreType) {
        try {
            // 证书持有者的name
            X500Name subject = new X500Name("CN=" + signedCertInfo.getCN()
                    + ",OU=" + signedCertInfo.getOU() + ",O="
                    + signedCertInfo.getO() + ",L=" + signedCertInfo.getL()
                    + ",ST=" + signedCertInfo.getST() + ",C="
                    + signedCertInfo.getC());
            issueSignedCert(signedCertInfo.getKeyStorePath(),
                    signedCertInfo.getKeyStorePass(), keyStoreType,
                    signedCertInfo.getIssuerAlias(),
                    signedCertInfo.getIssuerAliasPass(),
                    signedCertInfo.getSubjectAlias(),
                    signedCertInfo.getSubjectAliasPass(), subject,
                    signedCertInfo.getValidity(),
                    signedCertInfo.getSubjectPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 颁布证书
     * @param keyStorePath 秘钥库路径
     * @param keyStorePass  生成密钥库完整性检查密码
     * @param keyStoreType 秘钥库类型
     * @param issuerAlias  发行方证书别名
     * @param issuerAliasPass 恢复密钥的密码（证书发行方）
     * @param subjectAlias 数字证书名称
     * @param subjectAliasPass 恢复密钥的密码（证书持有方）
     * @param subject 数字证书主题
     * @param validity 数字证书有效天数
     * @param subjectPath 证书输出路径
     */
    private static void issueSignedCert(String keyStorePath,
                                        String keyStorePass, String keyStoreType, String issuerAlias,
                                        String issuerAliasPass, String subjectAlias,
                                        String subjectAliasPass, X500Name subject, int validity,
                                        String subjectPath) {
        FileOutputStream fos = null;
        FileOutputStream keyStoreFos = null;
        FileInputStream fis = null;

        try {
            //加载证书--------------------------------------------------------------------------------------
            //读取秘钥库文件
            fis = new FileInputStream(keyStorePath);
            //获取一个指定类型的秘钥库
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            //加载密钥库，密码可以解锁密钥库
            ks.load(fis, keyStorePass.toCharArray());
            //通过证书别名，获取与别名绑定的数字证书。
            X509Certificate issuerCert = (X509Certificate) ks.getCertificate(issuerAlias);
            //返回证书的编码形式。例如，X.509证书将被编码为ASN.1
            byte[] encoded = issuerCert.getEncoded();
            //获取证书实体
            X509CertImpl issuerCertImpl = new X509CertImpl(encoded);
            X509CertInfo issuerCertInfo = (X509CertInfo) issuerCertImpl.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);
            X500Name issuer = (X500Name) issuerCertInfo.get(X509CertInfo.SUBJECT + "." + CertificateIssuerName.DN_NAME);
            //通过与正版绑定的别名和发行密码，恢复秘钥。
            PrivateKey pk = (PrivateKey) ks.getKey(issuerAlias, issuerAliasPass.toCharArray());

            //颁布新证书--------------------------------------------------------------------------------------
            //说明，这里产生的只是模拟产生用户公钥，实际中是使用用户的公钥
            //封装X509证书信息
            X509CertInfo info = new X509CertInfo();
            //X509证书版本
            info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
            //X509证书序列号
            info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new Random().nextInt() & 0x7fffffff));
            //X509证书算法标识
            AlgorithmId aid = AlgorithmId.get(SIGN_ALGORITHM_SHA256);
            info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(aid));
            //X509证书主题
            info.set(X509CertInfo.SUBJECT, subject);
            //X509证书发行机构
            info.set(X509CertInfo.ISSUER, issuer);
            //X509证书公钥(这里应该放客户的公钥)
            info.set(X509CertInfo.KEY, new CertificateX509Key(issuerCert.getPublicKey()));
            //X509证书有效期
            Date fistDate = new Date();
            Date lastDate = new Date();
            lastDate.setTime(fistDate.getTime() + (validity * 24L * 60L * 60L * 1000));
            CertificateValidity interval = new CertificateValidity(fistDate, lastDate);
            info.set(X509CertInfo.VALIDITY, interval);

            //颁发的数字证书实体
            X509CertImpl cert = new X509CertImpl(info);
            //使用私钥对证书信息进行数字签名
            cert.sign(pk, SIGN_ALGORITHM_SHA256);

            //相关证书链
            X509Certificate[] chain = new X509Certificate[]{cert, issuerCert};
            //将给定的密钥分配给给定的别名，用给定的密码保护它。
            ks.setKeyEntry(subjectAlias, pk, subjectAliasPass.toCharArray(), chain);

            //导出秘钥库
            keyStoreFos = new FileOutputStream(keyStorePath);
            ks.store(keyStoreFos, keyStorePass.toCharArray());
            //导出数字证书
            fos = new FileOutputStream(subjectPath);
            fos.write(cert.getEncoded());
            fos.flush();

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | CertificateException | IOException | SignatureException | KeyStoreException | UnrecoverableKeyException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (keyStoreFos != null) {
                    keyStoreFos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从PFX格式的证书库，导出公钥证书
     *
     * @param storePathAndFileName  秘钥库文件
     * @param keyStorePass          秘钥库密码
     * @param alias                 证书别名
     * @param exportPathAndFileName 导出文件名
     */
    public static void exportPFXPublicKeyCertificate(
            String storePathAndFileName, String keyStorePass, String alias, String exportPathAndFileName) {

        exportPublicKeyCertificate(
                storePathAndFileName,
                keyStorePass,
                KEY_STORE_TYPE_PKCS12,
                alias,
                exportPathAndFileName
        );

    }

    /***
     *
     * 从JKS格式的证书库，导出公钥证书
     * @param storePathAndFileName 秘钥库文件
     * @param keyStorePass 秘钥库密码
     * @param alias 证书别名
     * @param exportPathAndFileName 导出文件名
     */
    public static void exportJKSPublicKeyCertificate(String storePathAndFileName, String keyStorePass, String alias, String exportPathAndFileName) {

        exportPublicKeyCertificate(
                storePathAndFileName,
                keyStorePass,
                KEY_STORE_TYPE_JKS,
                alias,
                exportPathAndFileName
        );

    }

    /***
     * 导出证书公钥证书
     * @param storePathAndFileName 秘钥库文件
     * @param keyStorePass 秘钥库密码
     * @param keyStoreType 秘钥库类型
     * @param alias 证书别名
     * @param exportPathAndFileName 导出文件名
     */
    public static void exportPublicKeyCertificate(
            String storePathAndFileName, String keyStorePass,
            String keyStoreType, String alias, String exportPathAndFileName) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            fis = new FileInputStream(storePathAndFileName);
            ks.load(fis, keyStorePass.toCharArray());
            Certificate cert = ks.getCertificate(alias);
            fos = new FileOutputStream(exportPathAndFileName);
            fos.write(cert.getEncoded());
            fos.flush();
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 生成.PFX数字证书
     * @param certInfo 证书详情
     */
    public static void generatePFX(KeyStoreInfo certInfo) {
        generateDigitalCert(
                KEY_STORE_TYPE_PKCS12, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, certInfo.getAlias(),
                certInfo.getKeyStorePass(), certInfo.getCertPass(),
                certInfo.getCN(), certInfo.getOU(), certInfo.getO(),
                certInfo.getL(), certInfo.getST(), certInfo.getC(),
                certInfo.getStart(), certInfo.getValidityDays(),
                certInfo.getPathAndFileName(), true
        );
    }

    public static void generatePFX(String alias, String keyStorePass,
                                   String certPass, String CN, String OU, String O, String L,
                                   String ST, String C, Date start, long validityDays,
                                   String pathAndFileName) {
        generateDigitalCert(
                KEY_STORE_TYPE_PKCS12, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, alias, keyStorePass, certPass, CN, OU,
                O, L, ST, C, start, validityDays, pathAndFileName, true
        );

    }

    /***
     * 增加新的证书到证书库
     * @param certInfo 证书详情
     */
    public static void addNewCert2PFX(KeyStoreInfo certInfo) {
        generateDigitalCert(
                KEY_STORE_TYPE_PKCS12, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, certInfo.getAlias(),
                certInfo.getKeyStorePass(), certInfo.getCertPass(),
                certInfo.getCN(), certInfo.getOU(), certInfo.getO(),
                certInfo.getL(), certInfo.getST(), certInfo.getC(),
                certInfo.getStart(), certInfo.getValidityDays(),
                certInfo.getPathAndFileName(), false
        );
    }

    public static void addNewCert2PFX(String alias, String keyStorePass,
                                      String certPass, String CN, String OU, String O, String L,
                                      String ST, String C, Date start, long validityDays,
                                      String pathAndFileName) {
        generateDigitalCert(
                KEY_STORE_TYPE_PKCS12, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, alias, keyStorePass, certPass, CN, OU,
                O, L, ST, C, start, validityDays, pathAndFileName, false
        );

    }

    /***
     * 生成.JKS数字证书
     * @param certInfo 证书详情
     */
    public static void generateJKS(KeyStoreInfo certInfo) {
        generateDigitalCert(
                KEY_STORE_TYPE_JKS, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, certInfo.getAlias(),
                certInfo.getKeyStorePass(), certInfo.getCertPass(),
                certInfo.getCN(), certInfo.getOU(), certInfo.getO(),
                certInfo.getL(), certInfo.getST(), certInfo.getC(),
                certInfo.getStart(), certInfo.getValidityDays(),
                certInfo.getPathAndFileName(), true
        );
    }

    public static void generateJKS(String alias, String keyStorePass,
                                   String certPass, String CN, String OU, String O, String L,
                                   String ST, String C, Date start, long validityDays,
                                   String pathAndFileName) {
        generateDigitalCert(
                KEY_STORE_TYPE_JKS, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, alias, keyStorePass, certPass, CN, OU,
                O, L, ST, C, start, validityDays, pathAndFileName, true
        );
    }

    /***
     * 增加新的证书到.JKS证书库
     * @param certInfo 证书详情
     */
    public static void addNewCert2JKS(KeyStoreInfo certInfo) {
        generateDigitalCert(
                KEY_STORE_TYPE_JKS, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, certInfo.getAlias(),
                certInfo.getKeyStorePass(), certInfo.getCertPass(),
                certInfo.getCN(), certInfo.getOU(), certInfo.getO(),
                certInfo.getL(), certInfo.getST(), certInfo.getC(),
                certInfo.getStart(), certInfo.getValidityDays(),
                certInfo.getPathAndFileName(), false
        );
    }

    public static void addNewCert2JKS(String alias, String keyStorePass,
                                      String certPass, String CN, String OU, String O, String L,
                                      String ST, String C, Date start, long validityDays,
                                      String pathAndFileName) {
        generateDigitalCert(
                KEY_STORE_TYPE_JKS, SIGN_ALGORITHM_SHA256,
                KEY_PAIR_ALGORITHM_RSA, SECURE_RANDOM_ALGORITHM,
                SECURE_RANDOM_PROVIDER, alias, keyStorePass, certPass, CN, OU,
                O, L, ST, C, start, validityDays, pathAndFileName, false
        );
    }

    /***
     *  创作CA根证书
     * @param keyStoreType  秘钥库类型
     * @param signAlgorithm 数字签名算法
     * @param keyPairAlgorithm 秘钥对算法
     * @param secureRandomAlgorithm 安全随机数算法（RNG算法的名称）
     * @param secureRandomProvider 安全随机数算法提供者的名称
     * @param alias 别名
     * @param keyStorePass 秘钥库密码
     * @param certPass 证书密码
     * @param CN 证书拥有者通用名
     * @param OU 组织单位名称
     * @param O 组织名称
     * @param L 城市或区域名称
     * @param ST 州或省份名称
     * @param C 国家名称
     * @param start 证书有效期开始日期/时间
     * @param validityDays 证书有效期天数
     * @param pathAndFileName 文件路径
     * @param createNew 是否创建新的
     */
    public static void generateDigitalCert(String keyStoreType,
                                           String signAlgorithm, String keyPairAlgorithm,
                                           String secureRandomAlgorithm, String secureRandomProvider,
                                           String alias, String keyStorePass, String certPass, String CN,
                                           String OU, String O, String L, String ST, String C, Date start,
                                           long validityDays, String pathAndFileName, boolean createNew) {
        FileOutputStream out = null;
        try {
            //一个高强度的随机数发生器（RNG）
            SecureRandom sr = SecureRandom.getInstance(secureRandomAlgorithm, secureRandomProvider);
            //证书注册机，生成秘钥对。参数分别为秘钥对算法、签名算法
            CertAndKeyGen cakg = new CertAndKeyGen(keyPairAlgorithm, signAlgorithm);
            cakg.setRandom(sr);
            //生成一对key参数为key的长度 对于RSA不能小于512
            cakg.generate(2048);
            //证书拥有者subject的描述name
            X500Name subject = new X500Name("CN=" + CN + ",OU=" + OU + ",O=" + O + ",L=" + L + ",ST=" + ST + ",C=" + C);
            //给证书配置扩展信息
//            PublicKey publicKey = cakg.getPublicKey();
//            PrivateKey privateKey = cakg.getPrivateKey();
//            CertificateExtensions certExts = new CertificateExtensions();
//            certExts.set("SubjectKeyIdentifier", new SubjectKeyIdentifierExtension((new KeyIdentifier(publicKey)).getIdentifier()));
//            certExts.set("AuthorityKeyIdentifier", new AuthorityKeyIdentifierExtension(new KeyIdentifier(publicKey), null, null));
//            certExts.set("BasicConstraints", new BasicConstraintsExtension(false,true,0));
//            X509Certificate certificate = cakg.getSelfCertificate(subject, start, validityDays * 24L * 60L * 60L,certExts);

            //配置证书的有效期,并生成根证书（自签名证书）
            X509Certificate certificate = cakg.getSelfCertificate(subject, start, validityDays * 24L * 60L * 60L);

            //创建KeyStore,存储证书
            KeyStore outStore = KeyStore.getInstance(keyStoreType);
            if (createNew) {
                outStore.load(null, keyStorePass.toCharArray());
                /**
                 *
                 * 将给定密钥（已经被保护）分配给给定别名。如果受保护密钥的类型为
                 * java.security.PrivateKey，则它必须附带证明相应公钥的证书链。
                 * 如果底层 keystore 实现的类型为jks，则必须根据 PKCS#8 标准中的定义将 key 编码为 EncryptedPrivateKeyInfo。
                 * 如果给定别名已经存在，则与别名关联的 keystore ，信息将被给定密钥（还可能包括证书链）重写。
                 * **/
                outStore.setKeyEntry(alias, cakg.getPrivateKey(), certPass.toCharArray(), new Certificate[]{certificate});
            } else {
                //将新的证书加入到证书库
                File f = new File(pathAndFileName);
                if (!f.exists()) {
                    throw new FileNotFoundException("证书库文件不存在,不能把新的证书加入到证书库!!!!!!!");
                }
                FileInputStream fis = new FileInputStream(f);
                outStore.load(fis, keyStorePass.toCharArray());
                fis.close();
                outStore.setKeyEntry(alias, cakg.getPrivateKey(), certPass.toCharArray(), new Certificate[]{certificate});
            }
            out = new FileOutputStream(pathAndFileName);
            //将此 keystore 存储到给定输出流，并用给定密码保护其完整性。
            outStore.store(out, keyStorePass.toCharArray());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | IOException | CertificateException | SignatureException | KeyStoreException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static String genCSR2() {
        try {

            //PKCS10格式文件
            String p10str =
                    "-----BEGIN CERTIFICATE REQUEST-----\n" +
                            "MIIDBTCCAe0CAQAwejELMAkGA1UEBhMCQ04xCzAJBgNVBAgMAmdkMQswCQYDVQQH\n" +
                            "DAJnejELMAkGA1UECgwCY3QxDTALBgNVBAsMBFRlc3QxHzAdBgkqhkiG9w0BCQEW\n" +
                            "EHRlc3RAZXhhbXBsZS5jb20xFDASBgNVBAMMCzE5Mi4xNzguMS4xMIIBIjANBgkq\n" +
                            "hkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7WZDMEU8x6ZOSYzTSWcdc33B4jKMaQvS\n" +
                            "hI0iA3VH5qZ6a2en6rfAUkNg+2HsPTmPK8n92MVTuCJCbASoV15ioUKQaWX4pur5\n" +
                            "28hro8w1EDGxjsQB2V6yzj0vyBbkj24HrnlCRmWPWy700ymp/26M/5XGFID6al0B\n" +
                            "JBhfK4cH4ltYiwFT7++/C/sZwfpWPooSBBCI1QdNbq3jQebD4NDaR2qQc6hNp+lS\n" +
                            "UWEpy8LrdqhwAtcvSjVt/iKprtor0cShNf8/BNiK5kK0SUae4u5yCItuHSLRZ4Ld\n" +
                            "qOuJbAOhvRZJOrLL/LSjGB0PzkW5TLMuuxYIgxEaC+gTIaYWBq2pOQIDAQABoEYw\n" +
                            "RAYJKoZIhvcNAQkOMTcwNTAzBgNVHREELDAqghB0ZXN0LmV4YW1wbGUuY29tghB0\n" +
                            "ZXN0LmV4bWFwbGUubmV0hwTAsgEBMA0GCSqGSIb3DQEBCwUAA4IBAQCE0Gqeq+b9\n" +
                            "EnrhWbcpKzDwA84V+Ac3tUgJmMb5xSTNXw1ljk77NV9h4LAdzb7PH6OCZjsdWDwV\n" +
                            "duh1wav3Kvl4vlqfQUz1bBcqL9k4LKa2jfTtVRsr1PRJnAJVAukIS78c/lUjYBP7\n" +
                            "Duf27tsfDnFL1g1uGcPQvcSk5yEDMEp3KRGy+bjZvN13UTFqOm2BDQbS6xM9lJ4f\n" +
                            "dLkMOziEVNQd7gSiHgwuahcabsj2Vvqolrv8oB4DOIAZ4pEFE1buO9h8ROwDpUAA\n" +
                            "HHpMwjJvoLDXlcUSBtCPsgFXbxT0DqVBCDBZkEQoEKVKEZU94ATgszPXXiE97ZaI\n" +
                            "DXO6Cz0pk/nZ\n" +
                            "-----END CERTIFICATE REQUEST-----";

            p10str = CertHelper.removeTagP10(p10str);
            //创建证书请求
            PKCS10 pkcs10 = new PKCS10(Base64.getMimeDecoder().decode(p10str));
            X500Name x500name = pkcs10.getSubjectName();
            String subject = x500name.getName();
            System.out.println(subject);
            List<String> list = Lists.newArrayList(StringUtils.split(subject, ","));
            for (String str : list) {
                System.out.println();
                System.out.println(str);
            }

            //获取公钥
            PublicKey publickey = pkcs10.getSubjectPublicKeyInfo();
            String pk = Base64.getMimeEncoder().encodeToString(publickey.getEncoded());
            System.out.println(pk);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static String getPublicKeyByP10() {
        try {

            //PKCS10格式文件
            String p10str =
                    "-----BEGIN CERTIFICATE REQUEST-----\n" +
                            "MIIDBTCCAe0CAQAwejELMAkGA1UEBhMCQ04xCzAJBgNVBAgMAmdkMQswCQYDVQQH\n" +
                            "DAJnejELMAkGA1UECgwCY3QxDTALBgNVBAsMBFRlc3QxHzAdBgkqhkiG9w0BCQEW\n" +
                            "EHRlc3RAZXhhbXBsZS5jb20xFDASBgNVBAMMCzE5Mi4xNzguMS4xMIIBIjANBgkq\n" +
                            "hkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7WZDMEU8x6ZOSYzTSWcdc33B4jKMaQvS\n" +
                            "hI0iA3VH5qZ6a2en6rfAUkNg+2HsPTmPK8n92MVTuCJCbASoV15ioUKQaWX4pur5\n" +
                            "28hro8w1EDGxjsQB2V6yzj0vyBbkj24HrnlCRmWPWy700ymp/26M/5XGFID6al0B\n" +
                            "JBhfK4cH4ltYiwFT7++/C/sZwfpWPooSBBCI1QdNbq3jQebD4NDaR2qQc6hNp+lS\n" +
                            "UWEpy8LrdqhwAtcvSjVt/iKprtor0cShNf8/BNiK5kK0SUae4u5yCItuHSLRZ4Ld\n" +
                            "qOuJbAOhvRZJOrLL/LSjGB0PzkW5TLMuuxYIgxEaC+gTIaYWBq2pOQIDAQABoEYw\n" +
                            "RAYJKoZIhvcNAQkOMTcwNTAzBgNVHREELDAqghB0ZXN0LmV4YW1wbGUuY29tghB0\n" +
                            "ZXN0LmV4bWFwbGUubmV0hwTAsgEBMA0GCSqGSIb3DQEBCwUAA4IBAQCE0Gqeq+b9\n" +
                            "EnrhWbcpKzDwA84V+Ac3tUgJmMb5xSTNXw1ljk77NV9h4LAdzb7PH6OCZjsdWDwV\n" +
                            "duh1wav3Kvl4vlqfQUz1bBcqL9k4LKa2jfTtVRsr1PRJnAJVAukIS78c/lUjYBP7\n" +
                            "Duf27tsfDnFL1g1uGcPQvcSk5yEDMEp3KRGy+bjZvN13UTFqOm2BDQbS6xM9lJ4f\n" +
                            "dLkMOziEVNQd7gSiHgwuahcabsj2Vvqolrv8oB4DOIAZ4pEFE1buO9h8ROwDpUAA\n" +
                            "HHpMwjJvoLDXlcUSBtCPsgFXbxT0DqVBCDBZkEQoEKVKEZU94ATgszPXXiE97ZaI\n" +
                            "DXO6Cz0pk/nZ\n" +
                            "-----END CERTIFICATE REQUEST-----";
            p10str = CertHelper.removeTagP10(p10str);
            //创建证书请求
            PKCS10CertificationRequest pkcs10CertificationRequest = new PKCS10CertificationRequest(Base64.getMimeDecoder().decode(p10str));
            System.out.println(pkcs10CertificationRequest.getSubject());
            //获取公钥
            SubjectPublicKeyInfo subjectPublicKeyInfo = pkcs10CertificationRequest.getSubjectPublicKeyInfo();
            System.out.println(Base64.getMimeEncoder().encodeToString(subjectPublicKeyInfo.getEncoded()));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //解析证书
    public static String cer() {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            String cer = "-----BEGIN CERTIFICATE-----\n" +
                    "MIIEDTCCAvWgAwIBAgIUP3s2ZiIua1ucPwjVCQJgwu8H8X0wDQYJKoZIhvcNAQEL\n" +
                    "BQAwWTELMAkGA1UEBhMCQ04xKDAmBgNVBAoMH0NoaW5hIFRlbGVjb20gR3Vhbmdk\n" +
                    "b25nIEFjYWRlbXkxIDAeBgNVBAMMF0d1YW5nZG9uZyBBY2FkZW15IFN1YkNBMB4X\n" +
                    "DTIwMDgwNTE1NTg1OFoXDTMwMDgwNTE1NTg1OFowcDELMAkGA1UEBhMCQ04xEjAQ\n" +
                    "BgNVBAgMCUd1YW5nRG9uZzESMBAGA1UEBwwJR3VhbmdaaG91MQ0wCwYDVQQKDARH\n" +
                    "U1RBMRMwEQYDVQQLDApTTUFMTCBDRUxMMRUwEwYDVQQDDAwxOTIuMTY4LjYuMTAw\n" +
                    "ggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCnLZgva/V488l7Wb/wxQdJ\n" +
                    "DC4CfRyOhOFUjz+K3sEkdSAUVOhJ29rFidzdfyPQaAZaYevU62GNRkvviSNKjxZ7\n" +
                    "+huRK0Ab1AaTCbecJicDKAlmB46DmU/BbBxxr6S8W2JH2vm2R3EwrKXcZ+24lRSD\n" +
                    "0afsIlk5MN/GJ2vnbydulvmqHdw8VOms2Ks8YXc7I2qWzDOGNzJHbjJprKHqBQj3\n" +
                    "d5MoxJi+XWqyx+070QKxujcDh//oTfp4C6t/6a3WnjqrzVF1AYTKITMpfSgf1v3l\n" +
                    "7a8badgEvFtQQBoeT1ZQGSCxRtXzMS2OrKT96xn3vozzne4/SojL1W62kIdfKOht\n" +
                    "AgMBAAGjgbUwgbIwHQYDVR0OBBYEFEbJG0fWdxCD56jk+s8Zitx1hym1MA4GA1Ud\n" +
                    "DwEB/wQEAwIGwDAzBgNVHREELDAqghBnc3RhLmV4YW1wbGUuY29tghBnc3RhLmV4\n" +
                    "bWFwbGUubmV0hwTAqAYKMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUEC6ZCbYo\n" +
                    "vFWRGDlIzQ/iJH74Sh4wHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFCAICMA0G\n" +
                    "CSqGSIb3DQEBCwUAA4IBAQAl1tyl4axV+KI0SldRH1v54qcVW17wJc+nOeHDWH6c\n" +
                    "F4jOIvXrxL66yqb2IBm/j3ykTREb+E3lgL1BQ1Nn55BB+Gt62P5nTQ8hNQPsEo9D\n" +
                    "a6nZZ6yWwhumJyD3o9EhDOgshYbbWU5lvj2jvBO1Kx0dEtC3z0p2S6sCsDSRv5S6\n" +
                    "00caQ8PSW6qSEIZvAjEPgpE2J0Ee+Dz/aDZZaaWg2r1nso0/2WvjLRMrnmXexI/+\n" +
                    "faSW9TkJ0bYQuBVWKO/MKdTg1vxj5IRSXBVNtbDnbSsG6E3B0OclqnfDBAfJ87q6\n" +
                    "wLHMhZn3D5eQK3q1/O3me+boRYpvG6URLWe9X4QKuCjr\n" +
                    "-----END CERTIFICATE-----";
            InputStream input = IOUtils.toInputStream(cer);
            X509Certificate cert = (X509Certificate) cf.generateCertificate(input);
            PublicKey publicKey = cert.getPublicKey();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
            System.out.println(publicKeyString.length());
            System.out.println("-----------------公钥--------------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------公钥--------------------");
            System.out.println(cert.toString());
            System.out.println("证书序列号：" + cert.getSerialNumber().toString(16));
            System.out.println("签名算法：" + cert.getSigAlgName());
            System.out.println("使用者信息：" + cert.getSubjectDN());
            System.out.println("颁发者信息：" + cert.getIssuerDN());
            System.out.println("颁发者信息：" + cert.getIssuerX500Principal().getName());
            System.out.println(cert.getNotAfter());


        } catch (CertificateException e) {
            e.printStackTrace();
        }

        return null;
    }


}