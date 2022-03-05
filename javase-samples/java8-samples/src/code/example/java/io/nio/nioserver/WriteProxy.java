package code.example.java.io.nio.nioserver;

import java.util.Queue;

public class WriteProxy {

    private MessageBuffer messageBuffer;
    private Queue<Message> writeQueue;

    public WriteProxy(MessageBuffer messageBuffer, Queue<Message> writeQueue) {
        this.messageBuffer = messageBuffer;
        this.writeQueue = writeQueue;
    }

    public Message getMessage() {
        return this.messageBuffer.getMessage();
    }

    public boolean enqueue(Message message) {
        return writeQueue.offer(message);
    }
}
