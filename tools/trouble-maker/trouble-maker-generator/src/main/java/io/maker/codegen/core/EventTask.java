package io.maker.codegen.core;

import java.util.Map;

public class EventTask<T extends Event> implements Map.Entry<T, EventHandler<T>> {

    private T event;
    private EventHandler<T> handler;

    @SuppressWarnings("unchecked")
    public <S extends Event> EventTask(S event, EventHandler<S> handler) {
        this.event = (T) event;
        this.handler = (EventHandler<T>) handler;
    }

    @Override
    public T getKey() {
        return event;
    }

    @Override
    public EventHandler<T> getValue() {
        return handler;
    }

    @Override
    public EventHandler<T> setValue(EventHandler<T> value) {
        EventHandler<T> oldHandler = this.handler;
        this.handler = value;
        return oldHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Event) {
            Event event = (Event) o;
            return this.event.equals(event);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return event.hashCode();
    }
}
