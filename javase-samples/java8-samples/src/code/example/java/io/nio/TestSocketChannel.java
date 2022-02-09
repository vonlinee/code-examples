package code.example.java.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class TestSocketChannel {

	public void server() throws IOException {
		//1.创建ServerSocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//2.监听端口
		serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
		while (true) {
			//3.拿到客户端的Channel
		    SocketChannel socketChannel = serverSocketChannel.accept();
		}
	}
	
	public void client() {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			boolean result = socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
			System.out.println(result);
		} catch (IOException e) {
			e.fillInStackTrace();
		}
	}
}
