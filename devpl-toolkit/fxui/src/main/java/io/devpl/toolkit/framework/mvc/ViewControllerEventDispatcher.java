package io.devpl.toolkit.framework.mvc;

import javafx.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * EventHandlerManager也是 EventDispatcher
 * @see com.sun.javafx.scene.NodeEventDispatcher
 */
public class ViewControllerEventDispatcher implements EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(ViewControllerEventDispatcher.class);

    private final ViewController controller;

    public ViewControllerEventDispatcher(ViewController viewController) {
        this.controller = viewController;
    }

    // 全局事件注册中心
    private static final Map<EventType<? extends Event>,
            EventHandler<? extends Event>> eventHandlerMap = new LinkedHashMap<>();

    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        eventHandlerMap.put(eventType, eventHandler);
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        @SuppressWarnings("unchecked")
        EventHandler<Event> handler = (EventHandler<Event>) eventHandlerMap.get(event.getEventType());
        if (handler != null) {
            handler.handle(event);
            event.consume();
        } else {
            log.warn("不存在事件 {}", event);
        }
        return event;
    }
}
