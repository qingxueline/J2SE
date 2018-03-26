package com.somnus.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by lyl on 2018/3/26 0026.
 */
public class JedisTest {

    @Test
    public void redisStringTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("runoobkey"));
    }

    @Test
    public void redisListTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
    }

    @Test
    public void redisKeysTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }
    }


    //直接使用RedisUtils实例进行数据库连接
    @Test
    public void redisInstanceTest1() {
        JedisUtil jedisUtil = JedisUtil.getInstance();
        JedisUtil.Strings strings = jedisUtil.new Strings();
        strings.set("nnn", "nnnn");
        System.out.println("-----" + strings.get("nnn"));
    }

    //通过RedisUtil实例获取Jedis连接对象；这样就可以用原生的方式使用；最后使用完后需要手动将其归还到池子中：
    @Test
    public void redisInstanceTest2() {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        for (int i = 0; i < 10; i++) {
            jedis.set("test", "test");
            System.out.println(i + "==" + jedis.get("test"));
        }
        JedisUtil.getInstance().returnJedis(jedis);
    }


    @Test
    public void redisInstanceTest3() throws IOException {
        JedisUtil jedisUtil = JedisUtil.getInstance();
        Jedis jedis = JedisUtil.getInstance().getJedis();
        Person p = new Person();
        p.setId(3);
        p.setName("测试数据");

        JedisUtil.Strings strings = jedisUtil.new Strings();
        strings.set("person", SerializationUtils.serialize(p));

        byte[] personBy = jedis.get(SafeEncoder.encode("person"));
        Person p1 = (Person) SerializationUtils.deserialize(personBy);
        System.out.println("id:" + p1.getId());
        System.out.println("name:" + p1.getName());
    }


}
