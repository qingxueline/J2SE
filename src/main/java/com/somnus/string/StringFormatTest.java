package com.somnus.string;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * @author lyl
 * @version V1.0
 * @project notice-api
 * @package com.gsta.notice.common.utils
 * @date 2018/04/22 1:11
 * @description
 */
public class StringFormatTest {
    @Test
    public void formatTest() {
        String msg= String.format("%nStatus:%s%nError Message:%s", 1111, "22222");
        System.out.println("msg = " + msg);

        msg = String.format("我是%s,你是%s", "张三", "李四");
        System.out.println("msg:" + msg);
        System.out.printf("字母a的大写是：%c %n", 'A');
        System.out.printf("3>7的结果是：%b %n", 3 > 7);
        System.out.printf("100的一半是：%d %n", 100 / 2);
        System.out.printf("100的16进制数是：%x %n", 100);
        System.out.printf("100的8进制数是：%o %n", 100);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50 * 0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50 * 0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50 * 0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50 * 0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');


        System.out.println(String.format("%1$2s %2$2s %3$2s %4$2s", "a", "b", "c", "d"));
        System.out.println(String.format("%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d"));
        System.out.println(String.format("%2s %2s %2s %2s", "a", "b", "c", "d"));
    }


    @Test
    public void messageFormatTest() {
       String msg = MessageFormat.format("我是{0}，你是{1}","张三","李四");
        System.out.println("msg = " + msg);
    }
}
