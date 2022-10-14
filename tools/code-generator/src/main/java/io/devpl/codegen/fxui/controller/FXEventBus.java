package io.devpl.codegen.fxui.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class FXEventBus {

    private boolean eventInherietance;

    private final SubscriberRegistry handlerRegistryMap = new SubscriberRegistry();

    public <E extends Event, T extends EventTarget> void register(
            final EventType<E> eventType, EventHandler<? super E> handler) {
        register(eventType, null, handler);
    }

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

    public <E extends Event> void publish(E event, Predicate<EventTarget> filter) {
        if (filter == null) {
            throw new NullPointerException("filter cannot be null!");
        }
        publish(null, event, filter);
    }

    @SuppressWarnings("unchecked")
    public <T extends EventTarget, E extends Event> void publish(T eventTarget, E event, Predicate<EventTarget> filter) {
        if (event.getTarget() != eventTarget) {
            // 要求事件子类实现 copyFor方法，并且返回值
            Event newEvent = event.copyFor(event.getSource(), eventTarget);
            event = (E) newEvent;
        }
        handleEvent(event, filter);
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void handleEvent(E event, Predicate<EventTarget> filter) {
        CopyOnWriteArrayList<EventHandlerRegistry<? extends Event>> eventHandlerRegistryList =
                handlerRegistryMap.getEventHandlerRegistries(event.getEventType());
        for (int i = 0; i < eventHandlerRegistryList.size(); i++) {
            EventHandlerRegistry<E> registry = (EventHandlerRegistry<E>) eventHandlerRegistryList.get(i);

            if (!filter.test(registry.getTarget())) {
                continue;
            }

            Class<? extends EventTarget> clazz = event.getTarget().getClass();
            CopyOnWriteArrayList<EventHandler<? super E>> eventHandlers = registry.eventHandlerMap.get(clazz);
            for (int i1 = 0; i1 < eventHandlers.size(); i1++) {
                eventHandlers.get(i).handle(event);
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
