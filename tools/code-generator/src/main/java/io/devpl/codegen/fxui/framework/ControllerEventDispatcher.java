package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

public class ControllerEventDispatcher implements EventDispatcher {

    private final EventBus bus;

    public ControllerEventDispatcher(EventBus eventBus) {
        this.bus = eventBus;
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        bus.fireEvent(event);
        return event;
    }
}
