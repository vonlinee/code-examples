package code.example.java.io.nio.nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class SocketAccepter implements Runnable {

	private int tcpPort = 0;
	private ServerSocketChannel serverSocket = null;
	private Queue<Socket> socketQueue = null; //客户端连接队列

	public SocketAccepter(int tcpPort, Queue<Socket> socketQueue) {
		this.tcpPort = tcpPort;
		this.socketQueue = socketQueue;
	}

	@Override
	public void run() {
		try {
			this.serverSocket = ServerSocketChannel.open();
			this.serverSocket.bind(new InetSocketAddress(tcpPort));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				//获取客户端连接
				SocketChannel socketChannel = this.serverSocket.accept();
				System.out.println("Socket accepted: " + socketChannel);
				//TODO 检查队列是否能容纳新的Socket
				this.socketQueue.add(new Socket(socketChannel)); //客户端入队
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
