package io.devpl.codegen.fxui.framework;

import javafx.event.EventType;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SubscriberRegistry {

    /**
     * All registered subscribers, indexed by event type.
     *
     * <p>The {@link CopyOnWriteArraySet} values make it easy and relatively lightweight to get an
     * immutable snapshot of all current subscribers to an event without any locking.
     */
    private final ConcurrentMap<EventType<?>, CopyOnWriteArraySet<Subscriber>> subscribers = new ConcurrentHashMap<>();
    private final EventBus bus;

    SubscriberRegistry(EventBus bus) {
        if (bus == null) {
            throw new NullPointerException();
        }
        this.bus = bus;
    }

    void register(EventType<?> eventType, Subscriber subscriber) {
        CopyOnWriteArraySet<Subscriber> subscribers = this.subscribers.get(eventType);
        if (subscribers == null) {
            subscribers = new CopyOnWriteArraySet<>();
            this.subscribers.put(eventType, subscribers);
        }
        subscribers.add(subscriber);
    }

    void unregister() {

    }

    public Iterator<Subscriber> getSubscribers(EventType<?> eventType) {
        CopyOnWriteArraySet<Subscriber> subscribers = this.subscribers.get(eventType);
        if (subscribers == null) {
            subscribers = new CopyOnWriteArraySet<>();
        }
        return subscribers.iterator();
    }
}
