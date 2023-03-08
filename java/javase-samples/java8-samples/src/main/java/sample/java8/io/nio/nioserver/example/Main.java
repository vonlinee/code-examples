package sample.java8.io.nio.nioserver.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import sample.java8.io.nio.nioserver.IMessageProcessor;
import sample.java8.io.nio.nioserver.Message;
import sample.java8.io.nio.nioserver.NioServer;
import sample.java8.io.nio.nioserver.http.HttpMessageReaderFactory;

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
        byte[] httpResponseBytes = httpResponse.getBytes(StandardCharsets.UTF_8);
        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message Received from socket: Socket ID " + request.socketId);
            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);
            writeProxy.enqueue(response);
        };
        
        NioServer server = new NioServer(9999, new HttpMessageReaderFactory(), messageProcessor);
        try {
        	server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("浏览器访问：http://localhost:9999/");
    }
}
