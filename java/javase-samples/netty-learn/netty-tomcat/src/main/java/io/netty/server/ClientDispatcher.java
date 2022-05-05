package io.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

/**
 * 
 */
public class ClientDispatcher extends SimpleChannelInboundHandler<HttpObject>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		// class io.netty.handler.codec.http.HttpObjectAggregator$AggregatedFullHttpRequest
		System.out.println(msg.getClass());
		System.out.println(ctx.getClass());
	}
}
