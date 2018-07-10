package com.somnus.socket.netty.msgpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //这里需要在MsgpackDecoder解码器里面定义写入方式是BaseResponse
            BaseResponse response = (BaseResponse) msg;
            System.out.println("server>> status:" + response.getStatus() + " message:" + response.getMessage());

            /**
             * 相应给客户端，必须使用BaseResponse对象，否则解码器无法解析，因为现在服务的和客户端使用的是同一个解码器。
             * 如果请求格式和响应格式需求要求不一样，那么必须给服务端和客户端定义不同的解码器。
             */
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
