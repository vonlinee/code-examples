package io.devpl.toolkit.framework.mvc;

import javafx.event.*;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * EventHandlerManager也是 EventDispatcher
 * Controller之间的事件传递就不需要有父子关系，全局事件总线
 * @see com.sun.javafx.scene.NodeEventDispatcher
 */
public class ViewControllerEventDispatcher implements EventDispatcher {

    private final ViewController controller;

    public ViewControllerEventDispatcher(ViewController viewController) {
        this.controller = viewController;
    }

    // 全局事件注册中心
    private static final Map<EventType<? extends Event>, CopyOnWriteArrayList<EventHandler<Event>>>
            eventHandlerMap = new LinkedHashMap<>();

    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        CopyOnWriteArrayList<EventHandler<Event>> eventHandlers = eventHandlerMap.get(eventType);
        if (eventHandlers == null) {
            eventHandlers = new CopyOnWriteArrayList<>();
            eventHandlerMap.put(eventType, eventHandlers);
        }
        @SuppressWarnings("unchecked")
        EventHandler<Event> handler = (EventHandler<Event>) eventHandler;
        eventHandlers.add(handler);
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        CopyOnWriteArrayList<EventHandler<Event>> eventHandlers = eventHandlerMap.get(event.getEventType());
        if (eventHandlers == null || eventHandlers.isEmpty()) {
            // 没有事件匹配
            return event;
        }
        for (EventHandler<Event> handler : eventHandlers) {
            handler.handle(event);
        }
        return event;
    }
}
