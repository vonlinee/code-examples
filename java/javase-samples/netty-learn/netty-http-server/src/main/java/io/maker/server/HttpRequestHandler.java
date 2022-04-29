package io.maker.server;

import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		
		log.info("{}", ctx.channel().id());
		
		String uri = msg.uri();
		HttpHeaders headers = msg.headers();
		HttpMethod method = msg.method();
		ByteBuf content = msg.content();
		String contentString = msg.content().toString(StandardCharsets.UTF_8);
		
		headers.get("content-type");
		
		String res = "I am OK";
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, 
				HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSON.toJSONString(res).getBytes(StandardCharsets.UTF_8)));
		response.headers().set("content-type", HttpHeaderValues.APPLICATION_JSON);
		response.headers().set("content-length", response.content().readableBytes());
		if (HttpUtil.isKeepAlive(msg)) {
			// 长连接
			response.headers().set("Connection", "keepAlive");
			ctx.writeAndFlush(response);
		} else {
			// 关闭连接
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
		ctx.flush();
	}
}
