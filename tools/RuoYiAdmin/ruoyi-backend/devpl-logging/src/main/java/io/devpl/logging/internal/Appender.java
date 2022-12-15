package io.devpl.logging.internal;

import java.util.EventListener;

/**
 * @see org.apache.logging.log4j.core.Appender
 */
public interface Appender extends EventListener {

    /**
     * Gets the name of this Appender.
     * @return name, may be null.
     */
    String getName();

    /**
     * 使用桥接模式
     * Logs a LogEvent using whatever logic this Appender wishes to use. It is typically recommended to use a
     * bridge pattern not only for the benefits from decoupling（解耦） an Appender from its implementation, but it is also
     * handy for sharing resources which may require some form of locking.
     * @param event The LogEvent.
     */
    void append(LogEvent event);
}