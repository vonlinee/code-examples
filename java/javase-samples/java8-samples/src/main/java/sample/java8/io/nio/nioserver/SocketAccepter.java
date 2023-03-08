package sample.java8.io.nio.nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 收到连接请求，进行入队操作，生产者
 */
public class SocketAccepter implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(SocketAccepter.class);

	private int tcpPort;
	private ServerSocketChannel serverSocket;
	private Queue<Socket> socketQueue; // 客户端连接队列

	public SocketAccepter(int tcpPort, Queue<Socket> socketQueue) {
		this.tcpPort = tcpPort;
		this.socketQueue = socketQueue;
	}

	@Override
	public void run() {
		try {
			// ServerSocketChannelImpl package-private
			this.serverSocket = ServerSocketChannel.open();
			this.serverSocket.bind(new InetSocketAddress(tcpPort));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				// 获取客户端连接
				SocketChannel socketChannel = this.serverSocket.accept();
				LOG.info("Socket accepted: " + socketChannel.getRemoteAddress());
				// TODO 检查队列是否能容纳新的Socket
				socketQueue.add(new Socket(socketChannel)); // 客户端入队
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
