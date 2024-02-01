package io.devpl.eventbus.ext;

public interface Invocation<T, R> {
    R invoke(T input) throws Exception;
}
