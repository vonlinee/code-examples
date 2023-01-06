package io.devpl.toolkit.framework.mvc;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class NamedEvent extends Event {
    public NamedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
