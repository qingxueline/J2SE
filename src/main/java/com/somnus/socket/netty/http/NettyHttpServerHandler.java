package com.somnus.socket.netty.http;


import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NettyHttpServerHandler extends ChannelHandlerAdapter {
    private HttpRequest req;
    private boolean isGet;
    private boolean isPost;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Map<String, String> prams = new HashMap<String, String>(1000);

        if (msg instanceof HttpRequest) {
            req = (HttpRequest) msg;
//            System.out.println("URI:" + req.getUri());
            if (HttpMethod.GET.equals(req.getMethod())) {
                System.out.println("走GET方法！");
                isGet = true;
            }

            if (HttpMethod.POST.equals(req.getMethod())) {
                System.out.println("走POST方法！");
                isPost = true;
            }
        }

        if (isPost) {
            if (msg instanceof HttpContent) {
                /**1、如果传递的格式是name=jso&id=1000,可以这样接收**/
                LastHttpContent httpContent = (LastHttpContent) msg;
                //创建解析对象
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(req);
                decoder.offer(httpContent);
                List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
                for (InterfaceHttpData pram : list) {
                    Attribute data = (Attribute) pram;
                    prams.put(data.getName(), data.getValue());
                }
                send(ctx, HttpResponseStatus.OK, new Gson().toJson(prams));

                /**2、如果传递的价格是json字符串，可以这样接收**/
//                LastHttpContent httpContent = (LastHttpContent) msg;
//                ByteBuf byteData = httpContent.content();
//                byte[] byteArray = new byte[byteData.capacity()];
//                byteData.readBytes(byteArray);
//                String content = new String(byteArray);
//                send(ctx, HttpResponseStatus.OK, content);
            }
        }

        if (isGet) {
            //创建解析对象
            QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());
            decoder.parameters().forEach((key, value) -> {
                // entry.getValue()是一个List, 只取第一个元素
                prams.put(key, value.get(0));
            });
            send(ctx, HttpResponseStatus.OK, new Gson().toJson(prams));
        }


    }

    public void send(ChannelHandlerContext ctx, HttpResponseStatus status, String message) {
        FullHttpResponse resp = null;
        try {
            String charsetName = "UTF-8";
            resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.wrappedBuffer(message.getBytes(charsetName)));
            //告诉浏览器编码方式
            resp.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
            //首部告诉浏览器报文中实体主体的大小
            resp.headers().set(HttpHeaders.Names.CONTENT_LENGTH, resp.content().readableBytes());
            //设置keep-alive，在一次TCP连接中可以持续发送多份数据而不会断开连接。通过使用keep-alive机制，可以减少tcp连接建立次数
            resp.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //发送http响应,主动断开连接
        ctx.write(resp).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("大哥，出错啦，检查下代码啦！！！");
        cause.printStackTrace();
        ctx.close();

    }
}