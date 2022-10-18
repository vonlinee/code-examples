package io.devpl.eventbus.fx;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * EventHandler集合
 * @param <T>
 */
public class EventHandlers<T extends Event> {

    private final LinkedList<EventHandler<? super T>>
            eventHandlers = new LinkedList<>();

    public void append(EventHandler<? super T> handler) {
        eventHandlers.addFirst(handler);
    }

    public void prepend(EventHandler<? super T> handler) {
        eventHandlers.addLast(handler);
    }

    public void removeFirst() {
        eventHandlers.removeFirst();
    }

    public List<EventHandler<? super T>> getEventHandlers() {
        return eventHandlers;
    }
}
