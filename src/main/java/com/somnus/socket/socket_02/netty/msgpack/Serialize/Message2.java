package com.somnus.socket.socket_02.netty.msgpack.Serialize;

import lombok.Data;
import org.msgpack.annotation.Message;

@Message
@Data
public class Message2 {
    public String name;

    public Message2() {

    }

    public Message2(String name) {
        this.name = name;
    }


}