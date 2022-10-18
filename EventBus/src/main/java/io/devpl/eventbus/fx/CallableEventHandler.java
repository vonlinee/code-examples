package io.devpl.eventbus.fx;

import javafx.event.Event;
import javafx.event.EventHandler;

public interface CallableEventHandler<T extends Event, R> extends EventHandler<T> {

    R handleAndReturn(T event);

    @Override
    void handle(T event);
}
