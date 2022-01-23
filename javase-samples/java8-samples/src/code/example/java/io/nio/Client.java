package code.example.java.io.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		
		try (Socket socket = new Socket()) {
			socket.connect(BioThreadServer.ipAddress);
			
			OutputStream os = socket.getOutputStream();
			
			os.write("Hello World".getBytes());
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
