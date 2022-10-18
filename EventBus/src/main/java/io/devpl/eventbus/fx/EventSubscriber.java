package io.devpl.eventbus.fx;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.lang.reflect.Method;

/**
 * 处理FXEvent
 * @param <T>
 */
public class EventSubscriber<T extends Event> implements EventHandler<T> {

    Method method;
    Object subsciber;

    @Override
    public void handle(T event) {
        if (event instanceof ParameterizedEvent) {
            ParameterizedEvent realEvent = (ParameterizedEvent) event;
        }
    }
}
