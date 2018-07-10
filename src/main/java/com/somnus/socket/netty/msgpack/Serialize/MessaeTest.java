package com.somnus.socket.netty.msgpack.Serialize;

import org.msgpack.MessagePack;

import java.io.IOException;

/**
 *
 * MessagePack序列化和反序列化测试
 * @author lyl
 */
public class MessaeTest {
    public static void main(String[] args) throws IOException {

        //参数封装
        MyMessage src = new MyMessage();
        src.name = "msgpack";
        src.version = 0.6;
        //list
        src.list.add("1111");
        src.list.add("2222");
        //map
        src.map.put("a", "aaaa");
        src.map.put("b", "bbbb");
        //自定义对象
        src.message = new Message2("=============");
        src.list2.add(new Message2("sssss_01"));
        src.list2.add(new Message2("sssss_02"));

        src.map2.put("01", new Message2("xxxx_01"));
        src.map2.put("02", new Message2("xxxx_02"));

        MessagePack msgpack = new MessagePack();

        // 序列化 Serialize
        byte[] bytes = msgpack.write(src);

        //反序列化 Deserialize
        MyMessage dst = msgpack.read(bytes, MyMessage.class);

        System.out.println(dst.name);
        System.out.println(dst.version);
        System.out.println(dst.list);
        System.out.println(dst.map);
        System.out.println(dst.message.name);
        System.out.println(dst.list2);
        System.out.println(dst.map2);

    }
}
