package io.fxsdk.eventbus;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * 每一个类型对应一个Invocation
 * @param <T>
 * @param <R>
 */
public class EventHandlerInvocation<T extends Event, R> implements EventHandler<T>, EventInvocation<T, R> {

    private final CallableEventHandler<T, R> handler;

    private R result;

    private volatile boolean resConsumed;

    public EventHandlerInvocation(CallableEventHandler<T, R> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(T event) {
        handler.handle(event);
    }

    @Override
    public R invoke(T input) {
        return null;
    }
}
