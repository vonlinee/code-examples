package io.devpl.toolkit.fxui.framework.eventbus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;

public final class FXEventBus implements EventBus {

    /**
     * use the {@link Group} as an EventManager
     */
    private final Group eventHandlers = new Group();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event> Subscriber addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        eventHandlers.addEventHandler(eventType, eventHandler);
        return new Subscriber(this, eventType, (EventHandler<? super Event>) eventHandler);
    }

    @Override
    public <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        eventHandlers.removeEventHandler(eventType, eventHandler);
    }

    @Override
    public void fireEvent(Event event) {
        eventHandlers.fireEvent(event);
    }
}