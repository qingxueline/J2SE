package com.somnus.security.certificate;

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
import java.util.Base64;

/**
 * @author lyl
 * @version 2020/5/14 0014 01:56:26
 */
public class P10Test {


    public static String genCSR() {
        try {

            String signalg = "SHA256WithRSA";
            int alglength = 2048;
            String keyAlg = "RSA";

            X500NameBuilder x500NameBld = new X500NameBuilder(BCStyle.INSTANCE);
            x500NameBld.addRDN(BCStyle.C, "AU");
            x500NameBld.addRDN(BCStyle.O, "ootest");
            x500NameBld.addRDN(BCStyle.L, "Melbourne");
            x500NameBld.addRDN(BCStyle.ST, "Victoria");
            x500NameBld.addRDN(BCStyle.EmailAddress, "feedback-crypto@bouncycastle.org");
            X500Name subject = x500NameBld.build();

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlg);
            keyGen.initialize(alglength);
            KeyPair kp = keyGen.generateKeyPair();
            PKCS10CertificationRequestBuilder builder = new PKCS10CertificationRequestBuilder(subject, SubjectPublicKeyInfo.getInstance(kp.getPublic().getEncoded()));
            JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(signalg);
            ContentSigner contentSigner = jcaContentSignerBuilder.build(kp.getPrivate());
            PKCS10CertificationRequest pkcs10 = builder.build(contentSigner);
            String p10Str = Base64.getMimeEncoder().encodeToString(pkcs10.getEncoded());
            return CertUtil.wrapP10(p10Str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        genCSR();
    }
}
