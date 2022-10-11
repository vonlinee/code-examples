package io.devpl.codegen.fxui.framework;

public final class EventSource<T> {

    private final T source;

    public EventSource(T source) {
        this.source = source;
    }

    public T getSource() {
        return source;
    }
}
