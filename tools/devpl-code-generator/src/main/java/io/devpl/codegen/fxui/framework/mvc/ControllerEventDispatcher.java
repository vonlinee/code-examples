package io.devpl.codegen.fxui.framework.mvc;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

public class ControllerEventDispatcher implements EventDispatcher {

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return event;
    }
}
