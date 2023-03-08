package sample.java8.io.nio.server.v3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
	public static void main(String[] args) throws IOException {
		int port = 9999; // the port to listen
		SocketChannel.open(new InetSocketAddress(port));
	}
}
