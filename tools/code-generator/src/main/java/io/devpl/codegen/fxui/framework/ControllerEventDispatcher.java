package io.devpl.codegen.fxui.framework;

import com.sun.javafx.event.BasicEventDispatcher;
import com.sun.javafx.event.EventHandlerManager;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * 负责Controller的事件分发
 * 不同于BasicEventDispatcher，事件一旦被捕获就不会继续传播
 *
 * @see com.sun.javafx.event.BasicEventDispatcher
 */
public class ControllerEventDispatcher extends BasicEventDispatcher {

    private final EventHandlerManager eventHandlerManager;

    public ControllerEventDispatcher(final Object eventSource) {
        this(new EventHandlerManager(eventSource));
    }

    public ControllerEventDispatcher(final EventHandlerManager eventHandlerManager) {
        this.eventHandlerManager = eventHandlerManager;
    }

    /**
     * 每一个EventDispatchChain可以包含多个EventDispatcher
     *
     * @param event
     * @param tail
     * @return
     */
    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return eventHandlerManager.dispatchEvent(event, tail);
    }

    public <T extends Event> void registerEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        eventHandlerManager.addEventHandler(eventType, eventHandler);
    }
}
