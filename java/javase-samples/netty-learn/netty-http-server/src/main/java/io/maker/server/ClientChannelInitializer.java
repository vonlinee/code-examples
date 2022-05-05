package io.maker.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.Cleanup;
import lombok.SneakyThrows;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private SslContext sslContext;

	/**
	 * 当一个新的连接被接受时，一个新的子Channel将会被创建，此方法用于初始化该Channel
	 */
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		
		// 至少一个ChannelHandler—该组件实现了服务器对从客户端接收的数据的处理，即它的业务逻辑
		// Netty对HTTP协议的封装，顺序有要求 责任链模式，双向链表Inbound/OutBound
		//ch.pipeline().addLast(new HttpResponseEncoder()); // HttpRequestDecoder 编码器
		//ch.pipeline().addLast(new HttpRequestDecoder()); // HttpRequestDecoder 解码器

		// 然后是业务处理逻辑对应的ChannelHandler
		ChannelPipeline cp = ch.pipeline();
		// support SSL/TLS
		// cp.addLast(new SslHandler(sslContext.newEngine(ch.alloc())));
		// decode and encode
		cp.addLast(new HttpServerCodec());
		// handle message-body of POST
		cp.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
		cp.addLast(new HttpServerExpectContinueHandler());
		cp.addLast(new HttpRequestHandler());
		
		System.out.println(ch);
	}

	@SneakyThrows
	private SslContext createSslContext1() {
		String keyStoreFilePath = "/root/.ssl/test.pkcs12";
		String keyStorePassword = "passwd";
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		@Cleanup
		InputStream inputStream = new FileInputStream(keyStoreFilePath);
		keyStore.load(inputStream, keyStorePassword.toCharArray());
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
		return SslContextBuilder.forServer(keyManagerFactory).build();
	}

    /**
     * 当一个新的连接被接受时，一个新的子Channel将会被创建，此方法用于初始化该Channel
     */
    public void initChannel1(SocketChannel ch) throws Exception {

        // 至少一个ChannelHandler—该组件实现了服务器对从客户端接收的数据的处理，即它的业务逻辑
        // Netty对HTTP协议的封装，顺序有要求 责任链模式，双向链表Inbound/OutBound
        //ch.pipeline().addLast(new HttpResponseEncoder()); // HttpRequestDecoder 编码器
        //ch.pipeline().addLast(new HttpRequestDecoder()); // HttpRequestDecoder 解码器

        // 然后是业务处理逻辑对应的ChannelHandler
        ChannelPipeline cp = ch.pipeline();
        // support SSL/TLS
        // cp.addLast(new SslHandler(sslContext.newEngine(ch.alloc())));
        // decode and encode
        cp.addLast(new HttpServerCodec());
        // handle message-body of POST
        cp.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        cp.addLast(new HttpServerExpectContinueHandler());
        cp.addLast(new HttpRequestHandler());
    }

    @SneakyThrows
    private SslContext createSslContext() {
        String keyStoreFilePath = "/root/.ssl/test.pkcs12";
        String keyStorePassword = "passwd";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        @Cleanup
        InputStream inputStream = new FileInputStream(keyStoreFilePath);
        keyStore.load(inputStream, keyStorePassword.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
        return SslContextBuilder.forServer(keyManagerFactory).build();
    }

}
