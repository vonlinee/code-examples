package netty.tcp.samples.transform;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NettyBlockingIO
 * 
 * Channel、 ChannelPipeline 和 ChannelHandler
 * 
 * 
 */
public class NettyNioServer {

	public static void main(String[] args) throws Exception {
		new NettyNioServer().server(8888);;
	}
	
	public void server(int port) throws Exception {
		
		final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
		
		// Each Channel will be handled by its own {@link EventLoop} to not block others.
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
							@Override
							public void channelActive(ChannelHandlerContext ctx) throws Exception {
								// 写入数据
								ChannelFuture future = ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
								System.out.println(future);
								// 监听写入操作
								future.addListener(new ChannelFutureListener() {
									@Override
									public void operationComplete(ChannelFuture future) throws Exception {
										System.out.println(future);
										if (future.isSuccess()) {
											System.out.println("写入成功!");
										}
									}
								});
							}
						});
					}
				});
			ChannelFuture future = serverBootstrap.bind().sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

}
