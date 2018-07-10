package com.somnus.socket.netty.http;


import com.sun.istack.internal.NotNull;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class NettyHttpServer {


    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));


    public static void main(String[] args) {
        try {
            new NettyHttpServer().run(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(int port) throws CertificateException, SSLException {

        // 配置SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }


        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            if (sslCtx != null) {
                                //设置ssl
                                ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            //Http消息解码器，它的作用是将多个消息转换成FullHttpResponse
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            //Htpp消息编码器，它的作用是将多个消息转换成FullHttpRequest
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            //用于大文件传输
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new NettyHttpServerHandler());

                        }
                    });

            ChannelFuture cf = b.bind("127.0.0.1",port).sync();
            System.out.println("访问地址：" + (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
