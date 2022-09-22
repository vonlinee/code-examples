package io.devpl.logging.internal;

import io.devpl.logging.Logger;

public abstract class AbstractlLogger implements Logger, LogEventPublisher {

    @Override
    public void publish(LogEvent logEvent) {

    }
}
