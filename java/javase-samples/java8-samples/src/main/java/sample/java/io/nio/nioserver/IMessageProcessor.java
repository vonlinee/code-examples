package sample.java.io.nio.nioserver;

public interface IMessageProcessor {
    void process(Message message, WriteProxy writeProxy);
}
