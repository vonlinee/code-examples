package code.example.java.io.nio.nioserver;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class NioServer {
    private SocketAccepter socketAccepter;
    private SocketProcessor socketProcessor;
    private final int tcpPort;
    private IMessageReaderFactory messageReaderFactory = null;
    private IMessageProcessor messageProcessor = null;

    // move 1024 to ServerConfig,客户端Socket队列
    private static final int INTIAL_QUEUE_CAPACITY = 1024;

    public NioServer(int tcpPort, IMessageReaderFactory messageReaderFactory, IMessageProcessor messageProcessor) {
        this.tcpPort = tcpPort;
        this.messageReaderFactory = messageReaderFactory;
        this.messageProcessor = messageProcessor;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        Queue<Socket> socketQueue = new ArrayBlockingQueue<>(INTIAL_QUEUE_CAPACITY);
        socketAccepter = new SocketAccepter(tcpPort, socketQueue);
        socketProcessor = new SocketProcessor(socketQueue, new MessageBuffer(), new MessageBuffer(), messageReaderFactory, messageProcessor);
    }

    public void start() throws IOException {
        //启动两个线程
        Thread accepterThread = new Thread(socketAccepter); //处理连接事件
        Thread processorThread = new Thread(socketProcessor); //处理读写事件
        accepterThread.start();
        processorThread.start();
    }
}