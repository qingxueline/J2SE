package com.somnus.security;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名数据结构分为4部分：headers#body#parameters#PathVariable
 * headers,body,parameters,PathVariable都还按照参照ASCII字典排序，其中headers、body、parameters是key=value的格式，并且要求body必须是json格式。
 * headers部分如下：appid=xxxx&nonce=xxxxx&timestamp=xxxxx
 *
 * @author lyl
 * @version 2020/11/30 0030 10:01:50
 */
@Slf4j
public class SignUtil {

    public static String sign(
            Map<String, String> headers, String body, Map<String, String[]> params, String[] paths) throws Exception {
        StringBuilder signData = new StringBuilder();
        if (headers != null && headers.size() > 0) {
            StringBuilder headStr = new StringBuilder();
            headers.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(
                            paramEntry -> {
                                headStr.append(paramEntry).append('&');
                            });
            signData.append(headStr.toString(), 0, headStr.length() - 1).append("#");
        }

        // 1、对body的处理（对应@RequestBody），对请求参数按照key=value的格式并接，并且参照ASCII字典排序。如：a="a"&c="c"&f="f"
        if (StringUtils.isNotBlank(body)) {
            StringBuilder sbStr = new StringBuilder();
            Map bodyMap = JSON.parseObject(body, Map.class);
            bodyMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(
                            paramEntry -> {
                                sbStr.append(paramEntry).append('&');
                            });
            signData.append(sbStr.toString(), 0, sbStr.length() - 1).append("#");
        }

        // 2、对parameters处理（对应@RequestParam），对请求参数按照key=value的格式并接，并且参照ASCII字典排序。如：a="a"&c="c"&f="f"
        if (params != null && params.size() > 0) {
            StringBuilder sbStr = new StringBuilder();
            params.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(
                            paramEntry -> {
                                String paramValue =
                                        String.join(
                                                ",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                                sbStr.append(paramEntry.getKey()).append("=").append(paramValue).append('&');
                            });
            signData.append(sbStr.toString(), 0, sbStr.length() - 1).append("#");
        }

        // 对path variable（对应@PathVariable），多个参数使用","分割，并且参照ASCII字典排序。如：1,2,3,a,b
        if (ArrayUtils.isNotEmpty(paths)) {
            String pathValues = String.join(",", Arrays.stream(paths).sorted().toArray(String[]::new));
            signData.append(pathValues);
        }
        log.info("签名数据：" + signData.toString());
        return Coder.sha1(signData.toString());
    }

    // 测试演示
    public static void main(String[] args)
            throws Exception {
        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlsVA0FZdgX4AuJicHHQnGd4pIWX1pxJsGGIud2opbc6ousfp8Jub2lyhi2/OGaoXLMNsvv5YYF9l8qQuJPOMRJGMIzjMMwvgr5CuBgDty8kySZ9ueQpzirqOvsdtqYZU/Mwyu5k40KDx3St5gRkZhE/hWfLGGNPZe/l6mF1JCieceud3cix6Hosw9QKRs1JkjHbgpTRMDO+c5s87oolJlVIl+w1NMk+ud6X9hL05VmGH7cQGOcMhoIpNbW9aCR1DZOiZlJe2wtO9K8/UzJr08hKn+eFoe/4YVVIW5uicsMRN4SUeZkIEHP/9aZN57LCpmFmMRPHkrfLbxOLqnntdOwIDAQAB";


        Map<String, String> headers = new HashMap<>();
        headers.put("appid", "admin");
        headers.put("nonce", "123456789");
        headers.put("timestamp", "123456789");

//    String body = "{\n" + "\t\"name\": \"hjzgg\",\n" + "\t\"age\": 26\n" + "}";
        Map<String, String[]> params = new HashMap<>();
        params.put("abc", new String[] {"123456"});

        String[] paths = new String[] {"113"};

        String sign = sign(headers, null, params, paths);


//    Map<String, Object> mp = RSACoder.initKey();
//    publicKey = RSACoder.getPublicKey(mp);
//    String privateKey = RSACoder.getPrivateKey(mp);

        // 对签名数据进行加密
        byte[] data = sign.getBytes();
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        String base64str = Base64.encodeBase64URLSafeString(encodedData);
        System.out.println("===sign1:"+sign);
        System.out.println("===signature:" + base64str);

        //签名数据进行解密
//    byte[] decoded = Base64.decodeBase64(base64str);
//    byte[] decodedData = RSACoder.decryptByPrivateKey(decoded, privateKey);
//    String outputStr = new String(decodedData);
//    System.out.println("===sign2:"+outputStr);
    }
}
