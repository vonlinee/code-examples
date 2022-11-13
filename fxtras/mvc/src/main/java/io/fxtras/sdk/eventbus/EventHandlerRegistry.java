package io.fxtras.sdk.eventbus;

import javafx.event.EventType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventHandlerRegistry {

    private final Map<EventType<?>, HandlerCollection> handlers = new ConcurrentHashMap<>();
}
