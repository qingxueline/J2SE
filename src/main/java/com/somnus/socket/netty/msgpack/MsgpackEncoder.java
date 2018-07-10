package com.somnus.socket.netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;


/**
 *
 * Encoder(编码器)
 * MsgpackEncoder继承自Netty中的MessageToByteEncoder类，
 * 并重写抽象方法encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
 * 它负责将Object类型的POJO对象编码为byte数组，然后写入到ByteBuf中
 */
public class MsgpackEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // 创建MessagePack对象
        MessagePack msgPack = new MessagePack();
        // 将对象编码为MessagePack格式的字节数组
        byte[] raw = msgPack.write(msg);
        // 将字节数组写入到ByteBuf中进行传输
        out.writeBytes(raw);
    }

}
