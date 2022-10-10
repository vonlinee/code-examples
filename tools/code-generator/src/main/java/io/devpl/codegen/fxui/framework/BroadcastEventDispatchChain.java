package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

import java.util.LinkedList;

/**
 * 广播事件分发
 */
public class BroadcastEventDispatchChain implements EventDispatchChain {

    private volatile boolean consumed;

    private final LinkedList<EventDispatcher> dispatchers;

    public BroadcastEventDispatchChain() {
        this.dispatchers = new LinkedList<>();
    }

    @Override
    public EventDispatchChain append(EventDispatcher eventDispatcher) {
        dispatchers.addLast(eventDispatcher);
        return this;
    }

    @Override
    public EventDispatchChain prepend(EventDispatcher eventDispatcher) {
        dispatchers.addFirst(eventDispatcher);
        return this;
    }

    @Override
    public Event dispatchEvent(Event event) {
        if (consumed) {
            return event;
        }
        consumed = true;
        if (!(event instanceof BroadcastEvent)) {
            return event;
        }
        Event returnEvent = null;
        // 广播事件分发
        for (EventDispatcher dispatcher : dispatchers) {
            returnEvent = dispatcher.dispatchEvent(event, this);
        }
        return returnEvent;
    }
}
