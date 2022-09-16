package io.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        HttpRequest req = null;
        if (msg instanceof HttpRequest) {
            req = (HttpRequest) msg;
        }
        // 传递到下一个
        ctx.fireChannelRead(req);
    }
}
