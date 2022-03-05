package io.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyServer {

    public static int port = 8888;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 子线程处理类 , Handler
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 客户端初始化处理
                    protected void initChannel(SocketChannel client) throws Exception {
                        // 无锁化串行编程
                        //Netty对HTTP协议的封装，顺序有要求
                        client.pipeline().addLast(new HttpResponseEncoder());// HttpResponseEncoder 编码器
                        client.pipeline().addLast(new HttpRequestDecoder()); // HttpRequestDecoder 解码器
                        client.pipeline().addLast(new MyNettyHandler());
                    }
                })
                // 针对主线程的配置 分配线程最大数量 128
                .option(ChannelOption.SO_BACKLOG, 128)
                // 针对子线程的配置 保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //3、启动服务器
        ChannelFuture f = server.bind(port).sync();
        System.out.println("Netty Server已启动，监听的端口是：" + port);
        f.channel().closeFuture().sync();
    }
}
