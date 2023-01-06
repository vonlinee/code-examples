package io.devpl.toolkit.framework.mvc;

import javafx.event.*;

public abstract class AbstractViewController implements ViewController {

    private static final EventDispatchChain viewControllerDispatcherChain
            = new ViewControllerEventDispatchChain();

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return viewControllerDispatcherChain;
    }

    @Override
    public EventDispatcher getEventDispatcher() {
        return null;
    }

    /**
     * Registers an event filter to this node. The filter is called when the
     * node receives an {@code Event} of the specified type during the capturing
     * phase of event delivery.
     * @param <T>         the specific event class of the filter
     * @param eventType   the type of the events to receive by the filter
     * @param eventFilter the filter to register
     * @throws NullPointerException if the event type or filter is null
     */
    public final <T extends Event> void addEventFilter(
            final EventType<T> eventType,
            final EventHandler<? super T> eventFilter) {

    }

    /**
     * Registers an event handler to this node. The handler is called when the
     * node receives an {@code Event} of the specified type during the bubbling
     * phase of event delivery.
     * @param <T>          the specific event class of the handler
     * @param eventType    the type of the events to receive by the handler
     * @param eventHandler the handler to register
     * @throws NullPointerException if the event type or handler is null
     */
    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {

    }
}
