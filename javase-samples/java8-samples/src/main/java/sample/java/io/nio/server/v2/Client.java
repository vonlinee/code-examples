package sample.java.io.nio.server.v2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static final InetSocketAddress SERVER_SOCKET_ADDRESS = new InetSocketAddress("localhost", 8888);
	
	public static void main(String[] args) {
		Client client = new Client();
		client.sendMessage();
	}
	
	public void sendMessage() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			if (line.trim().equals("exit")) {
				break;
			} else {
				if(!line.isEmpty()) {
					try (Socket socket = new Socket()) {
						socket.connect(SERVER_SOCKET_ADDRESS);
						if (socket.isConnected()) {
							if (!socket.isBound()) {
								System.out.println("客户端未绑定IP");
								continue;
							}
							//优化：写大量文本，数据过多时会导致连接关闭
							if (!socket.isOutputShutdown()) {
								OutputStream os = socket.getOutputStream();
								os.write(line.getBytes());
							}
						}
					} catch (IOException e) {
						if (e instanceof ConnectException) {
							System.out.println("连接失败:" + e.getMessage());
						}
					}
				}
			}
		}
		scanner.close();
	}
}
