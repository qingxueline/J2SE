package com.somnus.guava.cache3;

import org.junit.Test;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.guava.cache3
 * @date 2018/05/25 17:53
 * @description
 */
public class CacheTest {
    @Test
    public void getCode(){
        String key = "HOST_MESSAGE";
        //从缓存中获取主机信息
        Context<String, HostMessage> context = new Context<>(new HostMessageCache());
        HostMessage hostMessage = context.get(key);
        System.out.println("hostMessage = " + hostMessage);
    }
}
