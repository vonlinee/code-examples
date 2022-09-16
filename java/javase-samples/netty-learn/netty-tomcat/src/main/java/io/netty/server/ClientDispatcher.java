package io.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpVersion;

/**
 *
 */
public class ClientDispatcher extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // class io.netty.handler.codec.http.HttpObjectAggregator$AggregatedFullHttpRequest
        if (msg instanceof HttpMessage) {
            HttpMessage message = (HttpMessage) msg;

            HttpHeaders headers = message.headers();
            HttpVersion httpVersion = message.protocolVersion();
            DecoderResult decoderResult = message.decoderResult();

            System.out.println(decoderResult);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
