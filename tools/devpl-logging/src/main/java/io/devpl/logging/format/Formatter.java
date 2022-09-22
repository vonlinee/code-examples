package io.devpl.logging.format;

public interface Formatter<T> {
    T format(LogMessage message);
}
