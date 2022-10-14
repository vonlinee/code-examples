package io.devpl.codegen.fxui.controller;

import com.sun.javafx.event.EventHandlerManager;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

/**
 * @see com.sun.javafx.scene.NodeEventDispatcher
 */
public class ControllerEventDispatcher implements EventDispatcher {

    private final EventHandlerManager eventHandlerManager;

    public ControllerEventDispatcher(Object source) {
        this.eventHandlerManager = new EventHandlerManager(source);
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return eventHandlerManager.dispatchEvent(event, tail);
    }

    public EventHandlerManager getEventHandlerManager() {
        return eventHandlerManager;
    }
}
