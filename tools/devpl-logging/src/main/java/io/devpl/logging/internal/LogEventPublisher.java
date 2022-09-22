package io.devpl.logging.internal;

public interface LogEventPublisher {

    default void publish(LogEvent logEvent) {
        publish((Object) logEvent);
    }

    void publish(Object logEvent);
}
