package io.devpl.codegen.ui.fxui.frame;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * 使用其他的库比如 Guava EventBus，Andorid EventBus
 */
public class FXEventBus {

    private boolean eventInherietance;

    private final SubscriberRegistry handlerRegistryMap = new SubscriberRegistry();

    public <E extends Event, T extends EventTarget> void register(
            final EventType<E> eventType, EventHandler<? super E> handler) {
        register(eventType, null, handler);
    }

    @SuppressWarnings("unchecked")
    public <E extends Event, T extends EventTarget> void register(
            final EventType<E> eventType, T target, EventHandler<? super E> handler) {
        if (eventType == null) {
            throw new NullPointerException();
        }
        if (target == null) {
            target = (T) Event.NULL_SOURCE_TARGET;
        }
        if (handler == null) {
            throw new NullPointerException();
        }
        Class<? extends EventTarget> eventTargetClass = target.getClass();
        CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> registryList = handlerRegistryMap.getEventHandlerRegistries(eventType);
        EventHandlerRegistry<E> registry = new EventHandlerRegistry<>(target);
        registryList.add(registry.register(eventTargetClass, handler));
    }

    public <E extends Event> void publish(E event, Function<EventTarget, Boolean> matcher) {
        if (matcher == null) {
            throw new NullPointerException("matcher cannot be null!");
        }
        publish(null, event, matcher);
    }

    @SuppressWarnings("unchecked")
    public <T extends EventTarget, E extends Event> void publish(T eventTarget, E event, Function<EventTarget, Boolean> filter) {
        if (event.getTarget() != eventTarget) {
            // 要求事件子类实现 copyFor方法，并且返回值
            Event newEvent = event.copyFor(event.getSource(), eventTarget);
            event = (E) newEvent;
        }
        handleEvent(event, filter);
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void handleEvent(E event, Function<EventTarget, Boolean> filter) {
        CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> eventHandlerRegistryList =
                handlerRegistryMap.getEventHandlerRegistries(event.getEventType());
        for (int i = 0; i < eventHandlerRegistryList.size(); i++) {
            EventHandlerRegistry<E> registry = (EventHandlerRegistry<E>) eventHandlerRegistryList.get(i);

            if (!filter.apply(registry.getTarget())) {
                continue;
            }

            if (event.getTarget() == Event.NULL_SOURCE_TARGET) {
                // 广播该事件
                for (CopyOnWriteArrayList<EventHandler<? super E>> list : registry.eventHandlerMap.values()) {
                    for (EventHandler<? super E> handler : list) {
                        handler.handle(event);
                    }
                }
            }
        }
    }

    private static class SubscriberRegistry {
        /**
         * 为了适配 {@link Event#fireEvent(EventTarget eventTarget, Event event))
         * 点对点的通信
         */
        private final ConcurrentMap<EventType<? extends Event>, CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>>>
                handlerRegistryByEventType = new ConcurrentHashMap<>();

        public CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> getEventHandlerRegistries(EventType<?> eventType) {
            CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> eventHandlersWithTarget = handlerRegistryByEventType.get(eventType);
            if (eventHandlersWithTarget == null) {
                eventHandlersWithTarget = new CopyOnWriteArrayList<>();
                handlerRegistryByEventType.put(eventType, eventHandlersWithTarget);
            }
            return eventHandlersWithTarget;
        }

        public <E extends Event> void put(EventType<E> eventType, CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> eventHandlerRegistries) {
            handlerRegistryByEventType.put(eventType, eventHandlerRegistries);
        }
    }
}
