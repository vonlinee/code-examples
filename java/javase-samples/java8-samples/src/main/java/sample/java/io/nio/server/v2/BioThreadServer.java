package sample.java.io.nio.server.v2;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 利用线程池优化服务器
 * @author someone
 */
public class BioThreadServer implements Runnable {

	ServerSocket serverSocket;
	ExecutorService pool;
	public static InetSocketAddress ipAddress = new InetSocketAddress("localhost", 8888);
	
	public BioThreadServer(int intialThreadPoolSize) {
		pool = Executors.newFixedThreadPool(intialThreadPoolSize);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(ipAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class ClientSocketHandler extends Thread {
		private Socket socket; // 客户端Socket

		public ClientSocketHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			// 死循环处理读写事件
			// 优化：超过一定时间无读写事件自动关闭连接
			while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) { 
				if (socket.isConnected() && !socket.isInputShutdown()) {
					try {
						InputStream is = socket.getInputStream();
						byte[] buf = new byte[1024];
						int sum = 0;
						int flag = 0;
						StringBuilder sb = new StringBuilder();
						while ((flag = is.read(buf)) != -1) {
							sum = sum + flag;
							sb.append(new String(buf, StandardCharsets.UTF_8));
						}
						if (sb.length() > 0) {
							System.out.println("收到来自" + socket.getRemoteSocketAddress() + "的" + sum + "字节数据:");
							System.out.println(" > " + sb.toString());
						}
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
				printClientSocket(socket);
				pool.submit(new ClientSocketHandler(socket));// 为新的连接创建新的线程
			}
		}
	}
	
	private void printClientSocket(Socket socket) {
		try {
			boolean keepAlive = socket.getKeepAlive();
			int trafficClass = socket.getTrafficClass();
			InetAddress localAddress = socket.getLocalAddress();
			int localPort = socket.getLocalPort();
			InetAddress inetAddress = socket.getInetAddress();
			SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
			System.out.println("Connected: " + remoteSocketAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new BioThreadServer(20), "NioServer Thread").start();
	}
}
