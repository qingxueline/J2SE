package com.somnus.socket.socket_02.netty.marshalling;

import com.somnus.socket.socket_02.utils.GzipUtils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.FileOutputStream;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Req req = (Req) msg;
        System.out.println("Server : " + req.getId() + ", " + req.getName() + ", " + req.getRequestMessage());

        byte[] attachment = GzipUtils.ungzip(req.getAttachment());
        String path = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar + "002.jpg";
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(attachment);
        fos.close();

        //响应内容
        ctx.writeAndFlush(resp(req));
        //.addListener(ChannelFutureListener.CLOSE);//如果加入 addListener(ChannelFutureListener.CLOSE)表示异步写操作完成以后，主动断开和客户端的连接（实现短连接）。
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /***
     * 响应内容
     * @param req .
     * @return .
     */
    private Resp resp(Req req) {
        Resp resp = new Resp();
        resp.setId(req.getId());
        resp.setName("resp" + req.getId());
        resp.setResponseMessage("响应内容" + req.getId());
        return resp;
    }


}
