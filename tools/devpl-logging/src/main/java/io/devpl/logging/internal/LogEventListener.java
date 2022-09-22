package io.devpl.logging.internal;

import java.util.EventListener;

public interface LogEventListener<T extends LogEvent> extends EventListener {
    void onLogEvent(T event);
}
