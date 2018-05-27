package com.somnus.guava;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.guava
 * @date 2018/05/27 1:48
 * @description
 */
public class BaseEncodingTest {
    @Test
    public void base64EncodeTest() {
        String str = "hello";
        BaseEncoding baseEncoding = BaseEncoding.base64();
        //base64编码数据
        String s = baseEncoding.encode(str.getBytes());
        System.out.println("s:" + s);

        //base64反编码数据
        s= new String(baseEncoding.decode(s));
        System.out.println(str.equalsIgnoreCase(s));

    }
}
