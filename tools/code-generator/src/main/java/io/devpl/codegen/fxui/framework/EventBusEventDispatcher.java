package io.devpl.codegen.fxui.framework;

import javafx.event.*;

/**
 * 负责Controller的事件分发
 * 不同于BasicEventDispatcher，事件一旦被捕获就不会继续传播
 * @see com.sun.javafx.event.BasicEventDispatcher
 */
public class EventBusEventDispatcher implements EventDispatcher {

    private final EventBus BUS;

    public EventBusEventDispatcher() {
        this.BUS = new FXEventBus();
    }

    /**
     * 每一个EventDispatchChain可以包含多个EventDispatcher
     * @param event
     * @param tail
     * @return
     */
    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        invokeEventHandler(event);
        event.consume();
        return event;
    }

    private void invokeEventHandler(Event event) {
        BUS.fireEvent(event);
    }

    public <T extends Event> void registerEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        BUS.addEventHandler(eventType, eventHandler);
    }
}
