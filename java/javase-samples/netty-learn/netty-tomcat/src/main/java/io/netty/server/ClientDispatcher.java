package io.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

/**
 * 
 */
public class ClientDispatcher extends SimpleChannelInboundHandler<HttpObject>{

	public static final Logger logger = LoggerFactory.getLogger(ClientDispatcher.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		logger.info("HttpObject[{}]", msg);
	}
}
