package io.maker.server;

import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class HttpRequestDispatcher extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // class io.netty.handler.codec.http.HttpObjectAggregator$AggregatedFullHttpRequest

        ChannelPipeline ctxPipeline = ctx.pipeline();
        Channel channel = ctx.channel();
        ChannelPipeline channelPipeline = channel.pipeline();
        Channel channel1 = ctxPipeline.channel();
        Channel channel2 = channelPipeline.channel();
        HttpRequest request = null;
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
        }
        System.out.println(request);
        DefaultHttpRequest req = (DefaultHttpRequest) request;
        if (req != null) {
            String uri = req.uri();
            System.out.println(uri + " -> " + req.method());
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
