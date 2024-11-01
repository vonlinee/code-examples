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
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.ssl.SslHandler;

/**
 * NettyBlockingIO
 * 
 * Channel、 ChannelPipeline 和 ChannelHandler
 * 
 * 
 */
public class NettyOioServer {

	public static void main(String[] args) throws Exception {
		new NettyOioServer().server(8888);;
	}
	
	public void server(int port) throws Exception {
		
		final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
		
		// Each Channel will be handled by its own {@link EventLoop} to not block others.
		@SuppressWarnings("deprecation")
		EventLoopGroup group = new OioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group)
				.channel(OioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
							@Override
							public void channelActive(ChannelHandlerContext ctx) throws Exception {
								ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
								
								
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
