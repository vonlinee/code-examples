package io.maker.codegen.core;

import java.util.function.Predicate;

/**
 * 事件容器，具体类型由子类决定
 */
public abstract class EventContainer implements EventSource {

    @Override
    public void addListener(String name, EventHandler<? extends Event> handler) {

    }

    @Override
    public void removeListener(String name) {

    }

    @Override
    public void publish(Event event) {

    }

    @Override
    public <T> void publish(Event event, Predicate<T> filter) {

    }

    @Override
    public void fireEvent(String name) {

    }

    @Override
    public void cancel(String name) {

    }
}
