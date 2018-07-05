package com.somnus.socket.socket_02.netty.msgpack;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * 定义通信格式为BaseResponse
 * 1、传输的pojo对象一定要加上@Message注解，否则无法使用MessagePack进行编码;
 * 2、另一个就是必须要有默认的无参构造器，不然就会报如下的错误：
 * org.msgpack.template.builder.BuildContext build
 * SEVERE: builder: 这个问题在github上有个issue解释了
 *
 * @author lyl
 */
@Message
@Data
public class BaseResponse {
    private int status;
    private String message;
}