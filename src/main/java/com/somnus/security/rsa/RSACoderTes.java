package com.somnus.security.rsa;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试数据加密
 * Created by lyl on 2017/7/5 0005.
 */
public class RSACoderTes {

    private String publicKey;
    private String privateKey;

    @Before
    public void setUp() throws Exception {
//        Map<String, Object> keyMap = RSACoder.initKey();
//        publicKey = RSACoder.getPublicKey(keyMap);
//        privateKey = RSACoder.getPrivateKey(keyMap);
        publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPQ/Zw8s/7cYrG/ClRaIn4AJLJW5yc5PlBC6Ny0W7JAEn0gMkAGVFUvC/SU3uTXdl6p/SD4Kc3XhLQc4X04I0bpSnJ+M1p9VUOvAZN5xlmA58vW78yhFc4NwDAdhGrMrJGPxT5wyD9+G7TW0Jl1iOsHYDnL5aroN9YLkWDH23PjQIDAQAB";
        privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI9D9nDyz/txisb8KVFoifgAkslbnJzk+UELo3LRbskASfSAyQAZUVS8L9JTe5Nd2Xqn9IPgpzdeEtBzhfTgjRulKcn4zWn1VQ68Bk3nGWYDny9bvzKEVzg3AMB2EasyskY/FPnDIP34btNbQmXWI6wdgOcvlqug31guRYMfbc+NAgMBAAECgYAXEbGIRIEbExButEIO+s9koOsCA393gCNmxvH91h2JvkMqXOaA3pLLGzZ0Hr9CXpVbe9oStkUW1E4hRMjoQpRWBXiiCASLG9Q69/PqMcNEqMpSxyjQOLnGOlyptDYTugy1nMsOq3Q8oNf5SifWBNoWFAxsnQ0d9/E8J/EHPzGZ4QJBAOe6PRafWzAE7GOjD7jB3xaAvGSEvj+PLAwjlvAVtHHD34CpDj7N79tuOY9O2wQJukaA70QLpAMkxeKbklA57VUCQQCeRZ6TkYO2Q7f91WAml5uX05LUZy14IwSepwP/nycAL9WwppcPhaHSRYQVCgaSJ0OpFzEom7gYZix6dDpOqRlZAkBFfVb2YsFQrBlLY8nCKPlCYUadiiUXIFMgLMgFpSygdRGgHpI0CUOldO9z5zzMgDJJFD8sE6WSf8OLygVdY5GlAkBBrG3CmeznYm4kW7fMC7i6ZUZV8IJDiXRPSMo19Zo/T5oB9P5VqIOfNs051bEoUDaZIVB6ozLenPbz0Oqo3S3xAkEAoXq0VXyPBmHrIwNpcoNI9XQnvf4JspnavPIRhgPOP8DslFwRVWFBm0357K7npnQBi/0SOopxEeS7kac6w4pCNA==";
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
                privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);


    }

    @Test
    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);


        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);


    }
}
