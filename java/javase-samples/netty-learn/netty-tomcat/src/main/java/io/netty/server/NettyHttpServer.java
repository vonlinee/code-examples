package io.netty.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyHttpServer {

	private final int port;

	public NettyHttpServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		int port = 8888;
		new NettyHttpServer(port).start();
	}

	private void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {

			Bootstrap bootstrap;

			// Bootstrap用于配置启动项
			ServerBootstrap serverBootstrap = new ServerBootstrap()
					.group(group)
					.channel(NioServerSocketChannel.class) // 指定所使用的NIO传输Channel
					.localAddress(new InetSocketAddress(port))
					.childHandler(new ClientChannelInitializer());
			ChannelFuture future = serverBootstrap.bind().sync(); // sync()阻塞直到绑定完成
			future.channel().closeFuture().sync(); // 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
