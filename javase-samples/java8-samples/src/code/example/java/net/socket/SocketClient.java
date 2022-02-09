package code.example.java.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {

    Socket socket;

    public SocketClient() throws IOException {
        this.socket = new Socket();
        socket.bind(new InetSocketAddress(8888));
    }
}
