package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class MyChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        String s = buffer.toString(StandardCharsets.UTF_8);
        System.out.println(s);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
        System.out.println("读取完成");
    }
}
