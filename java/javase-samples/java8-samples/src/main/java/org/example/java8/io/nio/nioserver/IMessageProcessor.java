package org.example.java8.io.nio.nioserver;

public interface IMessageProcessor {
    void process(Message message, WriteProxy writeProxy);
}
