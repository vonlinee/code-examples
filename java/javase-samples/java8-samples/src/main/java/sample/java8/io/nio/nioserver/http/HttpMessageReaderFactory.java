package sample.java8.io.nio.nioserver.http;

import sample.java8.io.nio.nioserver.IMessageReader;
import sample.java8.io.nio.nioserver.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {
    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
