package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public final class Subscriber {

    private final EventBus bus;

    /**
     * 订阅者的相关信息
     */
    private final Object subscriber;
    private EventType<? extends Event> eventType;
    private EventHandler<? super Event> eventHandler;

    Subscriber(EventBus bus, Object subscriber, EventType<? extends Event> eventType, EventHandler<? super Event> eventHandler) {
        this.bus = bus;
        this.subscriber = subscriber;
        this.eventType = eventType;
        this.eventHandler = eventHandler;
    }

    /**
     * Stop listening for events.
     */
    public void unsubscribe() {
        bus.removeEventHandler(eventType, eventHandler);
    }

    public EventHandler<? super Event> getEventHandler() {
        return eventHandler;
    }
}
