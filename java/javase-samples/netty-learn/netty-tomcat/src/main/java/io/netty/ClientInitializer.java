package io.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 无锁化串行编程
        //Netty对HTTP协议的封装，顺序有要求
        // HttpResponseEncoder 编码器
        // 责任链模式，双向链表Inbound OutBound
        ChannelPipeline pipeline = ch.pipeline();
        if (pipeline instanceof DefaultChannelPipeline) {
            DefaultChannelPipeline p = (DefaultChannelPipeline) pipeline;
            p.addLast(new HttpResponseEncoder());
            // HttpRequestDecoder 解码器
            p.addLast(new HttpRequestDecoder());
            // 业务逻辑处理
            p.addLast(new Dispatcher());
        }
    }
}
