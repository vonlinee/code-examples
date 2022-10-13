package io.devpl.eventbus.ext;

@FunctionalInterface
public interface Callback {
    void call(Object result);
}
