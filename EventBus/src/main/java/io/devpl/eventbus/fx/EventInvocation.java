package io.devpl.eventbus.fx;

public interface EventInvocation<T, R> {
    R invoke(T input);
}
