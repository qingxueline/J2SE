package com.somnus.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Redis支持五种数据类型：string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)。
 * Created by lyl on 2018/3/26 0026.
 */
public class JedisTest {

    //string
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

    //list，数据重复
    @Test
    public void redisListTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //存储数据到列表中
        jedis.lpush("site-list", "a", "b", "c", "d", "d");

        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 10);//获取前10条数据
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
    }

    //除list等于value的元素，当count>0时，从表头开始查找，移除count个；当count=0时，从表头开始查找，移除所有等于value的；当count<0时，从表尾开始查找，移除|count| 个
    @Test
    public void lremTest() {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        System.out.println("结果： " + jedis.lrem("site-list", 0, "d"));
    }

    //set,数据不重复
    @Test
    public void redisSetTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        jedis.sadd("site-set", "a", "b", "c", "d", "d");

        //获取数据
        Set<String> smembers = jedis.smembers("site-set");
        for (String str : smembers) {
            System.out.println("列表项为: " + str);
        }
    }

    //移除集合Set key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
    @Test
    public void sremTest() {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        jedis.srem("site-set","d");
    }


    //获取数据库所有的key
    @Test
    public void redisKeysTest() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        System.out.println("keys = " + keys.size());
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("key:" + key);
        }
    }


    //删除key
    @Test
    public void deleteTest() {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        jedis.del("person");
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
            jedis.set("Serialize", "Serialize");
            System.out.println(i + "==" + jedis.get("Serialize"));
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
