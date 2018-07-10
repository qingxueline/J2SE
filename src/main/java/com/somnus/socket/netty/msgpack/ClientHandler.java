package com.somnus.socket.netty.msgpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseResponse response = (BaseResponse) msg;
        System.out.println("client>> status:" + response.getStatus() + " message:" + response.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送10个UserInfo给服务器，由于启用了粘包/拆包支持，所以这里连续发送多个也不会出现粘包的现象。
        for (int i = 0; i < 10; i++) {
            BaseResponse res = new BaseResponse();
            res.setStatus(i);
            res.setMessage(i + "message");
            //写完再flush，测试编解码效果。其实可以直接使用 ctx.writeAndFlush()
            ctx.write(res);
//            ctx.writeAndFlush(userInfo);
        }
        Thread.sleep(2000);
        ctx.flush();
        System.out.println("-----------------send over-----------------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("client error");
        ctx.close();
    }
}
