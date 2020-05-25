package com.somnus.security.certificate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;
import sun.security.pkcs10.PKCS10;
import sun.security.x509.X500Name;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 签名工具
 *
 * @author lyl
 * @version 2020/3/19 0019 15:17:08
 */
@Slf4j
public class CertHelper {

    public CertHelper() {
    }


    /**
     * 使用PFX证书私钥解码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param certPass
     * @param msg
     * @return
     */
    public static byte[] decodeByPFXPrivateKey(String storePath, String storePass, String alias, String certPass, byte[] msg) {
        return decodeByKeyStorePrivateKey(storePath, storePass, "pkcs12", alias, certPass, msg);
    }

    /**
     * 使用PFX证书私钥编码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param certPass
     * @param msg
     * @return
     */
    public static byte[] encodeByPFXPrivateKey(String storePath, String storePass, String alias, String certPass, byte[] msg) {
        return encodeByKeyStorePrivateKey(storePath, storePass, "pkcs12", alias, certPass, msg);
    }

    /**
     * 使用PFX证书公钥解码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param msg
     * @return
     */
    public static byte[] decodeByPFXPublicKey(String storePath, String storePass, String alias, byte[] msg) {
        return decodeByKeyStorePublicKey(storePath, storePass, "pkcs12", alias, msg);
    }

    /**
     * 使用PFX证书公钥编码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param msg
     * @return
     */
    public static byte[] encodeByPFXPublicKey(String storePath, String storePass, String alias, byte[] msg) {
        return encodeByKeyStorePublicKey(storePath, storePass, "pkcs12", alias, msg);
    }

    /**
     * 使用JKS证书私钥编码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param msg
     * @return
     */
    public static byte[] encodeByJKSPrivateKey(String storePath, String storePass, String alias, String certPass, byte[] msg) {
        return encodeByKeyStorePrivateKey(storePath, storePass, "jks", alias, certPass, msg);
    }

    /**
     * 使用JKS证书公钥编码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param msg
     * @return
     */
    public static byte[] encodeByJKSPublicKey(String storePath, String storePass, String alias, byte[] msg) {
        return encodeByKeyStorePublicKey(storePath, storePass, "jks", alias, msg);
    }

    /***
     * 使用JKS证书私钥解码
     * @param storePath
     * @param storePass
     * @param alias
     * @param certPass
     * @param msg
     * @return
     */
    public static byte[] decodeByJKSPrivateKey(String storePath, String storePass, String alias, String certPass, byte[] msg) {
        return decodeByKeyStorePrivateKey(storePath, storePass, "jks", alias, certPass, msg);
    }


    /**
     * 使用JKS证书公钥解码
     *
     * @param storePath
     * @param storePass
     * @param alias
     * @param msg
     * @return
     */
    public static byte[] decodeByJKSPublicKey(String storePath, String storePass, String alias, byte[] msg) {
        return decodeByKeyStorePublicKey(storePath, storePass, "jks", alias, msg);
    }


    /**
     * KeyStore公钥编码
     *
     * @param ks
     * @param alias
     * @param input
     * @return
     */
    public static byte[] encodeByKeyStorePublicKey(KeyStore ks, String alias, byte[] input) {
        try {
            PublicKey pk = ks.getCertificate(alias).getPublicKey();

            return crypt(Cipher.ENCRYPT_MODE, pk, input);
        } catch (KeyStoreException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] encodeByKeyStorePublicKey(String storePath, String storePass, String storeType, String alias, byte[] msg) {
        PublicKey pk = publicKeyInKeyStore(storePath, storePass, storeType, alias);
        return crypt(Cipher.ENCRYPT_MODE, pk, msg);
    }


    /**
     * KeyStore公钥解码
     *
     * @param ks
     * @param alias
     * @param input
     * @return
     */
    public static byte[] decodeByKeyStorePublicKey(KeyStore ks, String alias, byte[] input) {
        try {
            PublicKey pk = ks.getCertificate(alias).getPublicKey();
            return crypt(Cipher.DECRYPT_MODE, pk, input);
        } catch (KeyStoreException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] decodeByKeyStorePublicKey(String storePath, String storePass, String storeType, String alias, byte[] msg) {
        PublicKey pk = publicKeyInKeyStore(storePath, storePass, storeType, alias);
        return crypt(Cipher.DECRYPT_MODE, pk, msg);
    }


    /**
     * KeyStore私钥编码
     *
     * @param ks
     * @param alias
     * @param certPass
     * @param input
     * @return
     */
    public static byte[] encodeByKeyStorePrivateKey(KeyStore ks, String alias, String certPass, byte[] input) {
        try {
            PrivateKey pk = (PrivateKey) ks.getKey(alias, certPass.toCharArray());
            return crypt(Cipher.ENCRYPT_MODE, pk, input);
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }
        return null;
    }

    public static byte[] encodeByKeyStorePrivateKey(String storePath, String storePass, String storeType, String alias, String certPass, byte[] msg) {
        PrivateKey pk = privateKeyInKeyStore(storePath, storePass, storeType, alias, certPass);
        return crypt(Cipher.ENCRYPT_MODE, pk, msg);
    }


    /**
     * KeyStore私钥解码
     *
     * @param ks
     * @param alias
     * @param certPass
     * @param input
     * @return
     */
    public static byte[] decodeByKeyStorePrivateKey(KeyStore ks, String alias, String certPass, byte[] input) {
        try {
            PrivateKey pk = (PrivateKey) ks.getKey(alias, certPass.toCharArray());
            return crypt(Cipher.DECRYPT_MODE, pk, input);
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static byte[] decodeByKeyStorePrivateKey(String storePath, String storePass, String storeType, String alias, String certPass, byte[] msg) {
        PrivateKey pk = privateKeyInKeyStore(storePath, storePass, storeType, alias, certPass);
        return crypt(Cipher.DECRYPT_MODE, pk, msg);
    }

    /**
     * 一个用于编码和解码的功能函数
     *
     * @param opmode Cipher 的操作模式
     * @param key    加密密钥
     * @param input  保存结果的缓冲区
     * @return
     */
    private static byte[] crypt(int opmode, Key key, byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(opmode, key);
            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException var5) {
            var5.printStackTrace();
        }
        return null;
    }

    /**
     * 使用证书解码
     *
     * @param certPath
     * @param msgData
     * @return
     */
    public static byte[] encodeByCert(String certPath, byte[] msgData) {
        try {
            PublicKey pk = publicKeyInCert(certPath);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            byte[] data = cipher.doFinal(msgData);
            return data;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    /**
     * 使用证书解码
     *
     * @param certPath
     * @param msgData
     * @return
     */
    public static byte[] decodeByCert(String certPath, byte[] msgData) {
        try {
            PublicKey pk = publicKeyInCert(certPath);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pk);
            byte[] data = cipher.doFinal(msgData);
            return data;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    /***
     * 获取PFX证书公钥
     */
    public static PublicKey publicKeyInPFX(String storePath, String storePass, String alias) {
        return publicKeyInKeyStore(storePath, storePass, "pkcs12", alias);
    }

    /***
     * 获取JKS证书公钥
     */
    public static PublicKey publicKeyInJKS(String storePath, String storePass, String alias) {
        return publicKeyInKeyStore(storePath, storePass, "jks", alias);
    }

    /**
     * 获取证书私钥。私钥只有PFX和JKS的证书保存私钥
     *
     * @param storePath
     * @param storePass
     * @param storeType
     * @param alias
     * @return
     */
    public static PublicKey publicKeyInKeyStore(String storePath, String storePass, String storeType, String alias) {
        KeyStore ks = keyStoreLoad(storePath, storePass, storeType);
        try {
            assert ks != null;
            return ks.getCertificate(alias).getPublicKey();
        } catch (KeyStoreException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static PrivateKey privateKeyInJKS(String storePath, String storePass, String alias, String certPass) {
        return privateKeyInKeyStore(storePath, storePass, "jks", alias, certPass);
    }

    public static PrivateKey privateKeyInPFX(String storePath, String storePass, String alias, String certPass) {
        return privateKeyInKeyStore(storePath, storePass, "pkcs12", alias, certPass);
    }

    public static PrivateKey privateKeyInKeyStore(String storePath, String storePass, String storeType, String alias, String certPass) {
        KeyStore ks = keyStoreLoad(storePath, storePass, storeType);
        PrivateKey pk = null;

        try {
            pk = (PrivateKey) ks.getKey(alias, certPass.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        }
        return pk;
    }

    public static KeyStore keyStoreLoad(String storePath, String storePass, String storeType) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(storePath);
            KeyStore ks = KeyStore.getInstance(storeType);
            ks.load(fis, storePass.toCharArray());
            KeyStore var6 = ks;
            return var6;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException var20) {
            var20.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var19) {
                var19.printStackTrace();
            }

        }

        return null;
    }

    /***
     * 获取证书公钥
     * @param certPath 证书路径
     * @return 证书公钥
     */
    public static PublicKey publicKeyInCert(String certPath) {
        return Objects.requireNonNull(cert(certPath)).getPublicKey();
    }

    /***
     *  获取jks格式证书别名列表
     * @param storePath 秘钥库路径
     * @param storePass 秘钥库密码
     * @return List 别名列表
     */
    public static List<String> allAliasesInJKS(String storePath, String storePass) {
        return allAliasesInKeyStore(storePath, "jks", storePass);
    }

    /***
     *  获取PFX格式证书别名列表
     * @param storePath 秘钥库路径
     * @param storePass 秘钥库密码
     * @return List 别名列表
     */
    public static List<String> allAliasesInPFX(String storePath, String storePass) {
        return allAliasesInKeyStore(storePath, "pkcs12", storePass);
    }

    /**
     * 获取证书别名列表
     *
     * @param storePath    秘钥库路径
     * @param keyStoreType 秘钥库类型
     * @param storePass    秘钥库密码
     * @return List 别名列表
     */
    public static List<String> allAliasesInKeyStore(String storePath, String keyStoreType, String storePass) {
        List<String> aliases = new ArrayList<>();
        File f = new File(storePath);
        FileInputStream fis = null;

        try {
            KeyStore outStore = KeyStore.getInstance(keyStoreType);
            fis = new FileInputStream(f);
            outStore.load(fis, storePass.toCharArray());
            Enumeration e = outStore.aliases();

            while (e.hasMoreElements()) {
                String alias = (String) e.nextElement();
                aliases.add(alias);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException var25) {
            var25.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var24) {
                var24.printStackTrace();
            }

        }

        return aliases;
    }

    /**
     * 校验数字证书有效期
     *
     * @param certPath 数字证书
     */
    public static void verifyValidityDays(String certPath) {
        X509Certificate cert = (X509Certificate) cert(certPath);
        try {
            assert cert != null;
            cert.checkValidity(new Date());
        } catch (CertificateExpiredException | CertificateNotYetValidException var3) {
            var3.printStackTrace();
        }

    }

    /**
     * 验证数字签名
     *
     * @param fatherCertPath 根证书
     * @param sonCertPath    子证书
     */
    public static void verifySign(String fatherCertPath, String sonCertPath) {
        Certificate father = cert(fatherCertPath);
        Certificate son = cert(sonCertPath);
        try {
            assert father != null;
            assert son != null;
            son.verify(father.getPublicKey());
        } catch (InvalidKeyException | CertificateException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException var5) {
            var5.printStackTrace();
        }

    }

    /***
     * 获取证书
     * @param certPath 证书路径
     * @return Certificate
     */
    public static Certificate cert(String certPath) {
        FileInputStream fis = null;

        try {
            //获取证书工厂
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            fis = new FileInputStream(certPath);
            return cf.generateCertificate(fis);
        } catch (CertificateException | FileNotFoundException var14) {
            var14.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 输出证书信息
     *
     * @param certPath     证书地址
     * @param certPass     证书密码
     * @param keyStorePass 秘钥库密码
     * @param keyStoreType 秘钥库类型
     * @throws Exception
     */
    public static void printCert(String certPath, String certPass, String keyStoreType, String keyStorePass) throws Exception {
        KeyStore ks = KeyStore.getInstance(keyStoreType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, keyStorePass.toCharArray());
        fis.close();
        Enumeration enemas = ks.aliases();
        String alias = null;

        if (enemas.hasMoreElements()) {
            alias = (String) enemas.nextElement();
        }
        if (ks.isKeyEntry(alias)) {
            System.out.println("==============================alias:"+alias);
            PrivateKey prikey = (PrivateKey) ks.getKey(alias, certPass.toCharArray());
            System.out.println("==============================prikey:"+prikey.getFormat());
            //获取数字证书
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            System.out.println("cert = " + cert);
        }

    }
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

    public static PrivateKey getPrivateKey(String key) throws Exception {
        IOUtils.toByteArray(IOUtils.toInputStream(key));
        byte[] keyBytes = Base64.getMimeDecoder().decode(CertHelper.removeTagPrivateKeyStr(key));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }



}