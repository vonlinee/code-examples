package io.devpl.logging.internal;

import java.io.Closeable;

/**
 * LogWriter 对应一个
 */
public interface MessageWriter<T extends Message> extends Closeable {

    String getName();

    /**
     * Log in <code>Appender</code> specific way. When appropriate, Loggers will
     * call the <code>doAppend</code> method of appender implementations in order to
     * log.
     */
    void write(T message);

    boolean isClosed();

    int getOrder();
}
