package com.somnus.socket.socket_02.netty.msgpack.Serialize;

import lombok.Data;
import org.msgpack.annotation.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Message
@Data
public class MyMessage {
    public String name;
    public double version;
    public List<String> list = new ArrayList<String>();
    public Map<String, String> map = new HashMap<String, String>();
    public List<Message2> list2 = new ArrayList<Message2>();
    public Map<String, Message2> map2 = new HashMap<String, Message2>();
    public Message2 message;
}