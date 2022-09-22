package io.devpl.logging;

import io.devpl.logging.internal.Level;

public interface Logger {

    void log(Level level, Marker marker, String message, Throwable throwable, Object... args);

    boolean isEnabled(Level level);
}
