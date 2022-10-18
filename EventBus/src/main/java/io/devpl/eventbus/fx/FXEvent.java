package io.devpl.eventbus.fx;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class FXEvent extends Event {

    public static EventType<? extends FXEvent> ANY = new EventType<>(EventType.ROOT, "FX");

    public FXEvent() {
        this(ANY);
    }

    public FXEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public FXEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}