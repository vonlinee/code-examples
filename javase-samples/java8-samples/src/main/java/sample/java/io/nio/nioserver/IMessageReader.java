package sample.java.io.nio.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public interface IMessageReader {
    void init(MessageBuffer readMessageBuffer);
    void read(Socket socket, ByteBuffer byteBuffer) throws IOException;
    List<Message> getMessages();
}
