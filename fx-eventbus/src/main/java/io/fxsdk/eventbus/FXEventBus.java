package io.fxsdk.eventbus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class FXEventBus {

    private final EventHandlerRegistry registry = new EventHandlerRegistry();

    public void register(Object event) {
        EventType<? extends Event> eventType;
        if (!(event instanceof Event)) {
            eventType = ParameterizedEvent.EVENT_REGISTRATION;
        }
    }

    public <T extends Event> void register(EventType<T> eventType, EventHandler<T> handler) {

    }
}
