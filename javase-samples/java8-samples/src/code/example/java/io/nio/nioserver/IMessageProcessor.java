package code.example.java.io.nio.nioserver;

public interface IMessageProcessor {
    public void process(Message message, WriteProxy writeProxy);
}
