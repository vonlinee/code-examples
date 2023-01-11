package io.devpl.toolkit.fxui.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public final class MessageEvent extends Event {

    private static final long serialVersionUID = -3388823807425619785L;

    /**
     * Common MessageEvent.
     */
    public static final EventType<MessageEvent> ANY = new EventType<>(Event.ANY, "");

    public MessageEvent(EventType<? extends MessageEvent> eventType) {
        super(eventType);
    }

    public MessageEvent(Object source, EventTarget target, EventType<? extends MessageEvent> eventType) {
        super(source, target, eventType);
    }
}
