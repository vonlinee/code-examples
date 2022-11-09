package io.fxsdk.eventbus;

public interface EventInvocation<T, R> {
    R invoke(T input);
}
