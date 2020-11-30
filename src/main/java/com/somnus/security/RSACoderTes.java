package com.somnus.security;

import org.apache.commons.lang.CharEncoding;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Base64;

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
//        获取公钥
//        publicKey = RSACoder.getPublicKey(keyMap);
//        获取私钥
//        privateKey = RSACoder.getPrivateKey(keyMap);
        publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPQ/Zw8s/7cYrG/ClRaIn4AJLJW5yc5PlBC6Ny0W7JAEn0gMkAGVFUvC/SU3uTXdl6p/SD4Kc3XhLQc4X04I0bpSnJ+M1p9VUOvAZN5xlmA58vW78yhFc4NwDAdhGrMrJGPxT5wyD9+G7TW0Jl1iOsHYDnL5aroN9YLkWDH23PjQIDAQAB";
        privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI9D9nDyz/txisb8KVFoifgAkslbnJzk+UELo3LRbskASfSAyQAZUVS8L9JTe5Nd2Xqn9IPgpzdeEtBzhfTgjRulKcn4zWn1VQ68Bk3nGWYDny9bvzKEVzg3AMB2EasyskY/FPnDIP34btNbQmXWI6wdgOcvlqug31guRYMfbc+NAgMBAAECgYAXEbGIRIEbExButEIO+s9koOsCA393gCNmxvH91h2JvkMqXOaA3pLLGzZ0Hr9CXpVbe9oStkUW1E4hRMjoQpRWBXiiCASLG9Q69/PqMcNEqMpSxyjQOLnGOlyptDYTugy1nMsOq3Q8oNf5SifWBNoWFAxsnQ0d9/E8J/EHPzGZ4QJBAOe6PRafWzAE7GOjD7jB3xaAvGSEvj+PLAwjlvAVtHHD34CpDj7N79tuOY9O2wQJukaA70QLpAMkxeKbklA57VUCQQCeRZ6TkYO2Q7f91WAml5uX05LUZy14IwSepwP/nycAL9WwppcPhaHSRYQVCgaSJ0OpFzEom7gYZix6dDpOqRlZAkBFfVb2YsFQrBlLY8nCKPlCYUadiiUXIFMgLMgFpSygdRGgHpI0CUOldO9z5zzMgDJJFD8sE6WSf8OLygVdY5GlAkBBrG3CmeznYm4kW7fMC7i6ZUZV8IJDiXRPSMo19Zo/T5oB9P5VqIOfNs051bEoUDaZIVB6ozLenPbz0Oqo3S3xAkEAoXq0VXyPBmHrIwNpcoNI9XQnvf4JspnavPIRhgPOP8DslFwRVWFBm0357K7npnQBi/0SOopxEeS7kac6w4pCNA==";
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }


    //用于服务使用者向服务提供者发送信息
    @Test
    public void test1() throws Exception {

        /**
         * 公钥生成签名，私钥解密签名
         * 请求参数按照字段顺序排序 --> 使用sha()加密排序好的字符串生产签名 --> 使用RSA私钥加密签名 --> 客户端使用Base64()加密用于方便数据传输 --> 服务端使用Base64()解密密数据传输的数据 --> 服务端使用RSA私钥对数据进行解密 --> 取得sha()加密的签名数据 --> 服务端把请求参数按照字段顺序排序进行sha()加密 --> 用从客户端取得的签名和服务端生成的签名做比较 --> 匹配成功签名成功。数据传输过程中没有被改变。
         *
         */
        String name = "abc";
        String password = "123456";
        System.err.println("公钥加密——私钥解密");

        //客户端代码
        //按照字典顺序排序，然后使用sha()制作签名
        String shaSign1 = "name=" + name + "&password=" + password;
        shaSign1 = Coder.sha1(shaSign1);
        byte[] data = shaSign1.getBytes();
        //使用公钥，对签名进行RSA加密
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        //Base64加密，方便签名数据传输
        String sign = Coder.encryptBASE64(encodedData);
        System.err.println("sign：" + "\n\r" + sign);


        //服务端代码
        //Base64解密，得到加密后的数据
        byte[] decoded = Coder.decryptBASE64(sign);
        //RSA解密取得原始数据
        byte[] decodedData = RSACoder.decryptByPrivateKey(decoded, privateKey);
        String outputStr = new String(decodedData);

        String shaSign2 = "name=" + name + "&password=" + password;
        shaSign2 = Coder.sha1(shaSign2);

        if (shaSign2.equals(outputStr)) {
            System.err.println("加密前: " + shaSign1 + "\n\r" + "解密后: " + outputStr);
        }

    }

    /**
     * 私钥加密——公钥解密
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {

        //1、--------------------------------------------服务端代码，使用私钥加密
        System.err.println("私钥加密——公钥解密");
        //用于定义签名
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        //Base64加密，方便数据传输
        String encoded = Coder.encryptBASE64(encodedData);
        System.err.println(".............encoded：" + encoded);

        //2、--------------------------------------------客户端代码，使用公钥加密
        byte[] decoded = Coder.decryptBASE64(encoded);
        byte[] decodedData = RSACoder.decryptByPublicKey(decoded, publicKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
    }


    /**
     * 服务端私钥生成签名——客户端公钥验证签名
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {

        String name = "abc";
        String password = "123456";


        //1、--------------------------------------------服务端代码，使用私钥生成签名
        //按照字典顺序排序，然后使用sha()制作签名
        String sha1 = "name=" + name + "&password=" + password;
        sha1 = Coder.sha1(sha1);
        // 产生签名
        String sign = RSACoder.sign(sha1.getBytes(), privateKey);

        //2、--------------------------------------------客户端代码，使用公钥验证
        // 验证签名(代码放在服务使用者一方)

        String sha2 = "name=" + name + "&password=" + password;
        sha2 = Coder.sha1(sha2);
        boolean status = RSACoder.verify(sha2.getBytes(), publicKey, sign);
        System.err.println("状态:\r" + status);


    }
}
