package code.example.java.io.nio.nioserver;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
	private SocketAccepter socketAccepter = null;
	private SocketProcessor socketProcessor = null;
	private int tcpPort = 0;
	private IMessageReaderFactory messageReaderFactory = null;
	private IMessageProcessor messageProcessor = null;

	public Server(int tcpPort, IMessageReaderFactory messageReaderFactory, IMessageProcessor messageProcessor) {
		this.tcpPort = tcpPort;
		this.messageReaderFactory = messageReaderFactory;
		this.messageProcessor = messageProcessor;
	}

	public void start() throws IOException {
		Queue<Socket> socketQueue = new ArrayBlockingQueue<Socket>(1024); // move 1024 to ServerConfig
		socketAccepter = new SocketAccepter(tcpPort, socketQueue);
		socketProcessor = new SocketProcessor(socketQueue, new MessageBuffer(), new MessageBuffer(), messageReaderFactory, messageProcessor);
		//启动两个线程
		Thread accepterThread = new Thread(socketAccepter); //处理连接事件
		Thread processorThread = new Thread(socketProcessor); //处理读写事件
		accepterThread.start();
		processorThread.start();
	}
}