package code.example.java.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioThreadServer implements Runnable {

	ServerSocket serverSocket;
	ExecutorService pool;
	public static InetSocketAddress ipAddress = new InetSocketAddress("localhost", 8888);
	
	public BioThreadServer() {
		pool = Executors.newFixedThreadPool(20);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(ipAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static class SocketHandler extends Thread {
		private Socket socket; // 客户端Socket

		public SocketHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) { // 死循环处理读写事件
				if (socket.isConnected() && !socket.isInputShutdown()) {
					try {
						InputStream is = socket.getInputStream();
						byte[] buf = new byte[1024];
						int flag = 0;
						StringBuilder sb = new StringBuilder();
						while ((flag = is.read(buf)) != -1) {
							sb.append(new String(buf, StandardCharsets.UTF_8));
						}
						System.out.println("" + sb.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) { // 主线程死循环等待新连接到来
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (socket != null) {
				pool.submit(new SocketHandler(socket));// 为新的连接创建新的线程
			}
		}
	}
}
