package com.somnus.security.certificate;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author lyl
 * @version 2020/5/14 0014 01:56:26
 */
public class P10Test {


    public static CertificateRequest genCertificateRequest() {
        try {

            String signalg = "SHA256WithRSA";
            int alglength = 2048;
            String keyAlg = "RSA";

            String o = "00276DF";
            String ou = "SN_TEST_008";
            String cn = o + "_" + ou;

            X500NameBuilder x500NameBld = new X500NameBuilder(BCStyle.INSTANCE);
            x500NameBld.addRDN(BCStyle.CN, cn);
            x500NameBld.addRDN(BCStyle.C, "China");
            x500NameBld.addRDN(BCStyle.ST, "Guangdong");
            x500NameBld.addRDN(BCStyle.L, "Tianhe");
            x500NameBld.addRDN(BCStyle.O, o);
            x500NameBld.addRDN(BCStyle.OU, ou);
//            x500NameBld.addRDN(BCStyle.EmailAddress, "feedback@bouncycastle.org");
            X500Name subject = x500NameBld.build();

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlg);
            keyGen.initialize(alglength);
            KeyPair kp = keyGen.generateKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();

            PKCS10CertificationRequestBuilder builder = new PKCS10CertificationRequestBuilder(subject, SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
            JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(signalg);
            ContentSigner contentSigner = jcaContentSignerBuilder.build(kp.getPrivate());
            PKCS10CertificationRequest pkcs10 = builder.build(contentSigner);
            String p10Str = Base64.getMimeEncoder().encodeToString(pkcs10.getEncoded());
            String publicKeyStr = Base64.getMimeEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyStr = Base64.getMimeEncoder().encodeToString(privateKey.getEncoded());
            return CertificateRequest.builder()
                    .p10Str(CertHelper.wrapP10(p10Str))
                    .publicKeyStr(CertHelper.wrapPublicKey(publicKeyStr))
                    .privateKeyStr(CertHelper.wrapPrivateKey(privateKeyStr))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Builder
    @Data
    static class CertificateRequest {
        private String publicKeyStr;
        private String privateKeyStr;
        private String p10Str;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSON(genCertificateRequest()));
    }
}
