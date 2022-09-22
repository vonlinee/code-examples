package io.devpl.logging.format;

import io.devpl.logging.internal.Message;

public interface Formatter<T> {
    T format(Message message);
}
