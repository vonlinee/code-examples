package io.devpl.codegen.ui.fxui.frame;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 某一类型的事件的所有订阅者
 * @param <T> 事件类型
 */
@Data
public class EventHandlerRegistry<T> {

    private final EventTarget target;

     final ConcurrentMap<Class<? extends EventTarget>, CopyOnWriteArrayList<EventHandler<? super T>>>
            eventHandlerMap = new ConcurrentHashMap<>();

    public EventHandlerRegistry(EventTarget target) {
        this.target = target;
    }

    public EventHandlerRegistry<T> register(Class<? extends EventTarget> eventTargetClass, EventHandler<? super T> handler) {
        getEventHandlers(eventTargetClass).add(handler);
        return this;
    }

    public CopyOnWriteArrayList<EventHandler<? super T>> getEventHandlers(Class<? extends EventTarget> targetClass) {
        CopyOnWriteArrayList<EventHandler<? super T>> eventHandlers = this.eventHandlerMap.get(targetClass);
        if (eventHandlers == null) {
            eventHandlerMap.put(targetClass, eventHandlers = new CopyOnWriteArrayList<>());
        }
        return eventHandlers;
    }

    public EventTarget getTarget() {
        return target;
    }
}
