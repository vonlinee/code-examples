package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public final class FXEventBus implements EventBus {

    private final ConcurrentMap<EventType<? extends Event>, CopyOnWriteArraySet<Subscriber>>
            typedSubscribers = new ConcurrentHashMap<>();

    @Override
    public <T extends Event> Subscriber addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        Subscriber subscriber = new Subscriber(this, this, eventType, (EventHandler<? super Event>) eventHandler);
        CopyOnWriteArraySet<Subscriber> subscribers = typedSubscribers.get(eventType);
        if (subscribers == null) subscribers = new CopyOnWriteArraySet<>();
        subscribers.add(subscriber);
        typedSubscribers.put(eventType, subscribers);
        return subscriber;
    }

    @Override
    public <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        typedSubscribers.get(eventType).removeIf(subscriber -> subscriber.getEventHandler().equals(eventHandler));
    }

    @Override
    public void fireEvent(Event event) {
        broadcastEvent(event);
    }

    private void broadcastEvent(Event event) {
        CopyOnWriteArraySet<Subscriber> subscribers = typedSubscribers.get(event.getEventType());
        for (Subscriber subscriber : subscribers) {
            subscriber.getEventHandler().handle(event);
        }
    }
}
