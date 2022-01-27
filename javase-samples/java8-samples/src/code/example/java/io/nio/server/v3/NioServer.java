package code.example.java.io.nio.server.v3;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

public class NioServer {
	//javax.net.DefaultServerSocketFactory
	private ServerSocketFactory factory = ServerSocketFactory.getDefault();
	private ServerSocket serverSocket;
	
	public NioServer() {
		System.out.println("NIO SERVER CREATE");
	}
	
	public static InetSocketAddress ipAddress = new InetSocketAddress("localhost", 8888);
	
	public static void main(String[] args) {
		
	}
}
