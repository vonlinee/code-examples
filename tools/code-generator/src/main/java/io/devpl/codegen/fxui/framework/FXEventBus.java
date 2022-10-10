package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.Iterator;

public final class FXEventBus implements EventBus {

    private final SubscriberRegistry subscribers = new SubscriberRegistry(this);

    @Override
    public <T extends Event> Subscriber addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        Subscriber subscriber = new Subscriber(this, eventType, (EventHandler<? super Event>) eventHandler);
        subscribers.register(eventType, subscriber);
        return subscriber;
    }

    @Override
    public <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {

    }

    /**
     * @param event 事件对象
     * @see com.google.common.eventbus.EventBus#post(Object)
     */
    @Override
    public void fireEvent(Event event) {
        Iterator<Subscriber> subscribers = this.subscribers.getSubscribers(event.getEventType());
        while (subscribers.hasNext()) {
            subscribers.next().handleEvenet(event);
        }
    }
}
