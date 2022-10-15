package io.devpl.codegen.fxui.frame;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FXEvent extends Event {

    private final Map<String, Object> extra = new HashMap<>();

    public static final EventType<? extends FXEvent> ANY = new EventType<>(EventType.ROOT, "FX");
    public static final EventType<? extends FXEvent> DATA_SEND = new EventType<>(ANY, "FX-DATA_SEND");

    private Predicate<EventTarget> filter;

    public FXEvent() {
        this(ANY);
    }

    public FXEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public FXEvent(Object source, EventType<? extends Event> eventType) {
        super(source, null, eventType);
    }

    public FXEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public FXEvent put(String name, Object value) {
        extra.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) extra.get(name);
    }

    @Override
    public FXEvent copyFor(Object newSource, EventTarget newTarget) {
        return (FXEvent) super.copyFor(newSource, newTarget);
    }
}