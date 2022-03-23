package sample.java.io.nio.nioserver.http;

import sample.java.io.nio.nioserver.IMessageReader;
import sample.java.io.nio.nioserver.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {
    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
