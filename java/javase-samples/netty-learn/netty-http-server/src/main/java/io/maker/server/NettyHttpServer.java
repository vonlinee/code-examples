package io.maker.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServer {

    private static final int port = 9999;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(20);
        try {
            // 服务端启动
            ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_BROADCAST, null)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LocalChannelInitializer())
                    .childHandler(new RemoteChannelInitializer());

            ChannelFuture future = serverBootstrap.bind(port); // io.netty.bootstrap.AbstractBootstrap$PendingRegistrationPromise
            // System.out.println(future.getClass());
            future = future.sync();
            // System.out.println(future.getClass());

            future.addListener(future1 -> {
                if (future1.isSuccess()) {
                    log.info("server started at localhost:{}", port);
                    System.out.println("localhost:" + port + "/");
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
