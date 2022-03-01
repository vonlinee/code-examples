package code.example.java.io.nio.nioserver;

public interface IMessageProcessor {
    void process(Message message, WriteProxy writeProxy);
}
