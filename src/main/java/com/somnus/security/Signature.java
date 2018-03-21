package com.somnus.security;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * MD5和AHA简单数字签名测试
 * Created by lyl on 2017/5/23 0023.
 */
public class Signature {

    /**
     * 内容校验：
     * <br/>
     * 1、对请求参数按照key=value的格式并接，并且参照ASCII字典排序。如：a="a"&c="c"&f="f"
     * <br/>
     * 2、对并接好的内容进行MD5算法加密，然后再进行SHA算法加密
     *
     * @param params。
     * @return 。
     */
    public static boolean checkContent(Map<String, String> params) throws Exception {
        return verify(params.get("sign"), MD5Util.sha(MD5Util.md5(getSignCheckContent(params))));

    }


    protected static boolean verify(String sign1, String sign2) {
        return sign1.equals(sign2);
    }

    protected static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        } else {
            params.remove("sign");
            StringBuilder content = new StringBuilder();
            ArrayList<String> keys = new ArrayList<String>(params.keySet());
            Collections.sort(keys);

            for (int i = 0; i < keys.size(); ++i) {
                String key = keys.get(i);
                String value = params.get(key);
                content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
            }
            System.out.println("content2: " + content.toString());
            return content.toString();
        }
    }


    public static void main(String[] args) throws Exception {
        //内容排序
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append("1");
        sb.append("&orderid=").append("201702131044123456789455255");
        System.out.println("content1: " + sb.toString());
        //内容加密
        String sign = MD5Util.sha(MD5Util.md5(sb.toString()));
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "1");
        params.put("orderid", "201702131044123456789455255");
        params.put("sign", sign);
        //签名验证
        System.out.println("params = " + Signature.checkContent(params));
    }
}
