package io.devpl.codegen.fxui.frame;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

public class EventBusDispatcher implements EventDispatcher {

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return null;
    }
}
