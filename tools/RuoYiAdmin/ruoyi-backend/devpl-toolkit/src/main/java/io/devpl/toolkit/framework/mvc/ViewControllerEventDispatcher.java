package io.devpl.toolkit.framework.mvc;

import com.sun.javafx.event.EventHandlerManager;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

/**
 * @see com.sun.javafx.scene.NodeEventDispatcher
 */
public class ViewControllerEventDispatcher implements EventDispatcher {

    private EventHandlerManager eventHandlerManager;

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return eventHandlerManager.dispatchEvent(event, tail);
    }
}
