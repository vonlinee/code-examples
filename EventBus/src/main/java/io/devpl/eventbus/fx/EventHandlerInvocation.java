package io.devpl.eventbus.fx;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 每一个类型对应一个Invocation
 * @param <T>
 * @param <R>
 */
public class EventHandlerInvocation<T extends Event, R> implements EventHandler<T>, EventInvocation<T, R> {

    @NotNull
    private final CallableEventHandler<T, R> handler;

    @Nullable
    private R result;

    private volatile boolean resConsumed;

    public EventHandlerInvocation(@NotNull CallableEventHandler<T, R> handler) {
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
