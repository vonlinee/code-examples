package code.example.java.io.nio.nioserver.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import code.example.java.io.nio.nioserver.IMessageReader;
import code.example.java.io.nio.nioserver.Message;
import code.example.java.io.nio.nioserver.MessageBuffer;
import code.example.java.io.nio.nioserver.Socket;

/**
 * HTTP消息，服务器不止要处理单个Http协议的请求
 *
 * @author someone
 */
public class HttpMessageReader implements IMessageReader {

    private MessageBuffer messageBuffer = null;

    private final List<Message> completeMessages = new ArrayList<>();
    private Message nextMessage = null;

    public HttpMessageReader() {
    }

    @Override
    public void init(MessageBuffer readMessageBuffer) {
        this.messageBuffer = readMessageBuffer;
        this.nextMessage = messageBuffer.getMessage();
        this.nextMessage.metaData = new HttpHeaders();
    }

    @Override
    public void read(Socket socket, ByteBuffer byteBuffer) throws IOException {
        int bytesRead = socket.read(byteBuffer);
        byteBuffer.flip();
        if (byteBuffer.remaining() == 0) {
            byteBuffer.clear();
            return;
        }
        this.nextMessage.writeToMessage(byteBuffer);
        int endIndex = HttpUtil.parseHttpRequest(this.nextMessage.sharedArray, this.nextMessage.offset,
                this.nextMessage.offset + this.nextMessage.length, (HttpHeaders) this.nextMessage.metaData);
        if (endIndex != -1) {
            Message message = this.messageBuffer.getMessage();
            message.metaData = new HttpHeaders();
            message.writePartialMessageToMessage(nextMessage, endIndex);
            completeMessages.add(nextMessage);
            nextMessage = message;
        }
        byteBuffer.clear();
    }

    @Override
    public List<Message> getMessages() {
        return this.completeMessages;
    }
}
