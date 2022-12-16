package sample.netty.chapter4;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettySample2 {

	private final int port;

	public NettySample2(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		int port = 8888;
		new NettySample2(port).start();
	}

	private void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			// Bootstrap用于配置启动项
			ServerBootstrap serverBootstrap = new ServerBootstrap()
					.group(group)
					.channel(NioServerSocketChannel.class) // 指定所使用的NIO传输Channel
					.localAddress(new InetSocketAddress(port))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						/**
						 * 当一个新的连接被接受时，一个新的子Channel将会被创建，此方法用于初始化该Channel
						 */
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 至少一个ChannelHandler—该组件实现了服务器对从客户端接收的数据的处理，即它的业务逻辑
							
							// 然后是业务处理逻辑对应的ChannelHandler
							ch.pipeline().addLast(new MyChannelInboundHandler());
						}
					});
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
