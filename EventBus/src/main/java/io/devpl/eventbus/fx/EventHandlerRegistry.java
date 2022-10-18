package io.devpl.eventbus.fx;

import javafx.event.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @see com.sun.javafx.event.EventHandlerManager
 */
public class EventHandlerRegistry implements EventDispatcher {

    private final Map<Class<?>, EventHandlers<? extends Event>>
            parameterizedEventHandlerMappings = new HashMap<>();

    private final Map<EventType<? extends Event>,
            EventHandlers<? extends Event>> eventHandlerMappings = new HashMap<>();

    /**
     * 注册事件处理逻辑
     * @param eventType
     * @param handler
     * @param <T>
     */
    public <T extends Event> void register(EventType<T> eventType, EventHandler<T> handler) {
        @SuppressWarnings("unchecked")
        EventHandlers<T> handlers = (EventHandlers<T>) eventHandlerMappings.get(eventType);
        if (handlers == null) {
            handlers = new EventHandlers<>();
            eventHandlerMappings.put(eventType, handlers);
            handlers.append(handler);
        }
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return null;
    }
}
