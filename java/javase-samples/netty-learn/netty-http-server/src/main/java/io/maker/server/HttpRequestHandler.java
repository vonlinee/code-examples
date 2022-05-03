package io.maker.server;

import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public class HttpRequestHandler extends SimpleChannelInboundHandler<HttpObject> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		// class io.netty.handler.codec.http.HttpObjectAggregator$AggregatedFullHttpRequest
		System.out.println("HttpObject => " + msg.getClass());
		System.out.println(ctx.channel().hashCode());
		System.out.println(ctx.channel().pipeline().hashCode());
		
		// 类型实际上是
		HttpRequest request = null;
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
		}

		String res = "I am OK";
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(JSON.toJSONString(res).getBytes(StandardCharsets.UTF_8)));
		response.headers().set("content-type", HttpHeaderValues.APPLICATION_JSON);
		response.headers().set("content-length", response.content().readableBytes());
		
		
		if (HttpUtil.isKeepAlive(request)) {
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
