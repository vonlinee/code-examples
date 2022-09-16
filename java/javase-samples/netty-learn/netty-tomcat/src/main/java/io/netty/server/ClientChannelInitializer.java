package io.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    public static SocketChannel ch;

    /**
     * 当一个新的连接被接受时，一个新的子Channel将会被创建，此方法用于初始化该Channel
     */
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // Netty对HTTP协议的封装，顺序有要求 责任链模式，双向链表Inbound/OutBound
        // 解码在前，编码在后
        ch.pipeline().addLast("I-HttpRequestDecoder", new HttpRequestDecoder()); // HttpRequestDecoder 解码器
        ch.pipeline().addLast("O-HttpResponseEncoder", new HttpResponseEncoder()); // HttpRequestDecoder 编码器
        // 然后是业务处理逻辑对应的ChannelHandler
        ch.pipeline().addLast("I-HttpServerHandler", new HttpServerHandler());
        ch.pipeline().addLast("I-Dispatcher", new ClientDispatcher());
        ClientChannelInitializer.ch = ch;
    }
}
