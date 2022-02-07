package code.example.java.io.nio.nioserver.example;

import java.io.IOException;

import code.example.java.io.nio.nioserver.IMessageProcessor;
import code.example.java.io.nio.nioserver.Message;
import code.example.java.io.nio.nioserver.Server;
import code.example.java.io.nio.nioserver.http.HttpMessageReaderFactory;

/**
 * 教程地址：
 * http://tutorials.jenkov.com/java-nio/non-blocking-server.html
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!!</body></html>";
        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");
        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message Received from socket: " + request.socketId);
            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);
            writeProxy.enqueue(response);
        };
        Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);
        server.start();
    }
}
