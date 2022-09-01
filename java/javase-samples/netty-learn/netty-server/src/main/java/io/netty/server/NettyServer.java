package io.netty.server;

import java.net.SocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundInvoker;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyServer {

    public static int port = 8888;

    //ChannelInitializer => ChannelInboundHandlerAdapter  生产者
    //ChannelInboundHandlerAdapter 消费者
    
    //ChannelPipeline 类似于双向队列
    //ChannelInboundInvoker, ChannelOutboundInvoker  生产，消费处理
    
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 子线程处理类 , Handler
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 客户端初始化处理
                    protected void initChannel(SocketChannel client) throws Exception {
                    	//客户端连接
                    	System.out.println("客户端连接建立成功" + client.remoteAddress());
                        // 无锁化串行编程
                        ChannelPipeline pipeline = client.parent().pipeline(); //NioSocketChannel
                        System.out.println(client.getClass());
                        
                        //Netty对HTTP协议的封装，顺序有要求
                        client.pipeline().addLast(new HttpResponseEncoder());// HttpResponseEncoder 编码器
                        client.pipeline().addLast(new HttpRequestDecoder()); // HttpRequestDecoder 解码器
                        client.pipeline().addLast(new MyNettyHandler());
                        client.pipeline().addLast(new MyOutboundHandler());
                    }
                })
                .handler(new ServerOutboundHandler())
                // 针对主线程的配置 分配线程最大数量 128
                .option(ChannelOption.SO_BACKLOG, 128)
                // 针对子线程的配置 保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //3、启动服务器
        ChannelFuture f = server.bind(port).sync();
        System.out.println("Netty Server已启动，监听的端口是：" + port);
        f.channel().closeFuture().sync();
    }
    
    /**
     * 服务端处理客户端连接关闭
     * @author someone
     */
    private static class ServerOutboundHandler extends ChannelOutboundHandlerAdapter {

		@Override
		public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise)
				throws Exception {
			
			super.bind(ctx, localAddress, promise);
		}

		@Override
		public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
				ChannelPromise promise) throws Exception {
			super.connect(ctx, remoteAddress, localAddress, promise);
		}

		@Override
		public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
			super.disconnect(ctx, promise);
		}

		@Override
		public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
			super.close(ctx, promise);
		}

		@Override
		public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
			super.deregister(ctx, promise);
		}

		@Override
		public void read(ChannelHandlerContext ctx) throws Exception {
			super.read(ctx);
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			super.write(ctx, msg, promise);
		}

		@Override
		public void flush(ChannelHandlerContext ctx) throws Exception {
			super.flush(ctx);
		}
    }
}
