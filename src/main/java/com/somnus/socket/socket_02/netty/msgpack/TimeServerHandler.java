package com.somnus.socket.socket_02.netty.msgpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //这里需要在MsgpackDecoder解码器里面定义写入方式是BaseResponse
            BaseResponse response = (BaseResponse) msg;
            System.out.println("server>> status:" + response.getStatus() + " message:" + response.getMessage());

            //相应给客户端，必须使用BaseResponse对象，否则解码器无法解析。
            response.setMessage("服务器成功接收信息");
            ctx.write(response);
        } finally {
            ctx.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("server error");
        ctx.close();
    }
}
