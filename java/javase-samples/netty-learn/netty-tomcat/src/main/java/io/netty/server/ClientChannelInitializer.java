package io.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * 当一个新的连接被接受时，一个新的子Channel将会被创建，此方法用于初始化该Channel
	 */
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		// 至少一个ChannelHandler—该组件实现了服务器对从客户端接收的数据的处理，即它的业务逻辑
		ch.pipeline().addLast();

		// Netty对HTTP协议的封装，顺序有要求 责任链模式，双向链表Inbound/OutBound
		ch.pipeline().addLast(new HttpResponseEncoder()); // HttpRequestDecoder 编码器
		ch.pipeline().addLast(new HttpRequestDecoder()); // HttpRequestDecoder 解码器
		// 然后是业务处理逻辑对应的ChannelHandler
		ch.pipeline().addLast(new HttpServerHandler());
		
		ch.pipeline().addLast(new ClientDispatcher());
	}
}
