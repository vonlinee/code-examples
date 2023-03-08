package sample.java8.net.socket;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketClient {

    InetSocketAddress SERVER_ADDR = new InetSocketAddress("localhost", 8888);
    InetSocketAddress CLIENT_ADDR_1 = new InetSocketAddress("localhost", 7777);

    @Test
    public void server() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(SERVER_ADDR);
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client() {
        Socket socket = new Socket();
        try {
            socket.bind(CLIENT_ADDR_1);
            socket.connect(SERVER_ADDR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client1() {

    }
}
