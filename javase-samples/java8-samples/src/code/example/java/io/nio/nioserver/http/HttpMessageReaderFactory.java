package code.example.java.io.nio.nioserver.http;

import code.example.java.io.nio.nioserver.IMessageReader;
import code.example.java.io.nio.nioserver.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {
    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
