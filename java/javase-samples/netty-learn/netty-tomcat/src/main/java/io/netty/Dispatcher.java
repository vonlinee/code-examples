package io.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.tomcat.http.GPRequest;
import io.netty.tomcat.http.GPResponse;
import io.netty.tomcat.http.GPServlet;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher extends ChannelInboundHandlerAdapter {

    private final Map<String, GPServlet> servletMapping = new HashMap<>();

    public void put(String name, GPServlet servlet) {
        servletMapping.put(name, servlet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("hello");
            HttpRequest req = (HttpRequest) msg;
            // 转交给我们自己的request实现
            GPRequest request = new GPRequest(ctx, req);
            // 转交给我们自己的response实现
            GPResponse response = new GPResponse(ctx, req);
            // 实际业务处理
            String url = request.getUrl();
            if (servletMapping.containsKey(url)) {
                servletMapping.get(url).service(request, response);
            } else {
                response.write("404 - Not Found");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }
}
