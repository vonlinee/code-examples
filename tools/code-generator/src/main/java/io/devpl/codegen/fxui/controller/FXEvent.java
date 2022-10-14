package io.devpl.codegen.fxui.controller;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.HashMap;
import java.util.Map;

public class FXEvent extends Event {

    private final Map<String, Object> extra = new HashMap<>();

    public static final EventType<? extends FXEvent> ANY = new EventType<>("FX");

    public static final EventType<? extends FXEvent> DATA_SEND = new EventType<>("FX-DATA_SEND");

    public FXEvent() {
        this(ANY);
    }

    public FXEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public FXEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public void putExtra(String name, Object value) {
        extra.put(name, value);
    }

    public <T> T getExtra(String name) {
        return (T) extra.get(name);
    }
}