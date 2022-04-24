package sample.netty.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

public class MyChannelInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx); // 触发事件
		System.out.println("channelActive => " + ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		log("channelRead", ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		Channel channel = ctx.channel();
		log("channelRegistered", ctx);
		ByteBuf buf = Unpooled.copiedBuffer("服务器已接收你的请求", CharsetUtil.UTF_8);
		ChannelFuture future = channel.writeAndFlush(buf);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					System.out.println("Write successful");
				} else {
					System.err.println("Write error");
					future.cause().printStackTrace();
				}
			}
		});
	}
	
	private void log(String eventName, ChannelHandlerContext ctx) {
		StringBuilder sb = new StringBuilder();
		Channel channel = ctx.channel();
		sb.append(eventName);
		if (channel instanceof SocketChannel) {
			sb.append("[SocketChannel] => ").append(channel);
		}
		if (channel instanceof ServerSocketChannel) {
			sb.append("[ServerSocketChannel] => ").append(channel);
		}
		System.out.println(sb.toString());
	}
}
