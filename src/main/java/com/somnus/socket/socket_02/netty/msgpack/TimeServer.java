package com.somnus.socket.socket_02.netty.msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class TimeServer {

    /**
     * 表示的是包的最大长度，超出包的最大长度netty将会做一些特殊处理
     **/
    private static int maxFrameLength = 65535;
    /**
     * 指的是长度域的偏移量，表示跳过指定长度个字节之后的才是长度域
     **/
    private static int lengthFieldOffset = 0;
    /**
     * 记录该帧数据长度的字段本身的长度
     **/
    private static int lengthFieldLength = 2;
    /**
     * 该字段加长度字段等于数据帧的长度，包体长度调整的大小，长度域的数值表示的长度加上这个修正值表示的就是带header的包
     **/
    private static int lengthAdjustment = 0;
    /**
     * 从数据帧中跳过的字节数，表示获取完一个完整的数据包之后，忽略前面的指定的位数个字节，应用解码器拿到的就是不带长度域的数据包
     **/
    private static int initialBytesToStrip = 2;

    public void bind(int port) throws Exception {
        EventLoopGroup bossGruop = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGruop, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip));
                            channel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                            channel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            channel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            channel.pipeline().addLast(new TimeServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGruop.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 12580;
        try {
            new TimeServer().bind(port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}