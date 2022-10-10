package io.devpl.codegen.fxui.framework;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class MessageEvent extends BroadcastEvent {

    public static EventType<MessageEvent> SEND_DATA = new EventType<>("send-message");

    public MessageEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public MessageEvent(final @NamedArg("source") Object source,
                        final @NamedArg("target") EventTarget target,
                        final @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
