package netty.tcp.samples.echo;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

	private String host;
	private int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class) // 客户端使用的Channel类型
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});
			ChannelFuture f = bootstrap.connect(new InetSocketAddress(host, port)).sync();
			initFutureListener(f);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initFutureListener(ChannelFuture future) {
		// 异步监听回调
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
//					System.in.read();
//					Scanner scanner = new Scanner(System.in);
//					while (scanner.hasNext()) {
//						String nextLine = scanner.nextLine();
//						ByteBuf buf = Unpooled.copiedBuffer(nextLine, StandardCharsets.UTF_8);
//						// DefaultChannelPromise
//						ChannelFuture wf = future.channel().writeAndFlush(buf);
//						// 是否写成功
//						if (wf.isSuccess()) {
//							System.out.println(wf);
//						}
//					}

					// scanner.close();
				} else {
					// 如果失败，获取异常信息
					Throwable cause = future.cause();
					System.out.println(cause.getMessage());
				}
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		String host = "localhost";
		int port = 8888;
		new EchoClient(host, port).start();
	}
}
