package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * 广播事件
 */
public class BroadcastEvent extends Event {

    public static final EventType<BroadcastEvent> ANY = new EventType<>(Event.ANY, "BROADCAST");

    public BroadcastEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public BroadcastEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
