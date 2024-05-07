package org.example.java8.io.nio.nioserver.http;

import org.example.java8.io.nio.nioserver.IMessageReader;
import org.example.java8.io.nio.nioserver.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {
    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
