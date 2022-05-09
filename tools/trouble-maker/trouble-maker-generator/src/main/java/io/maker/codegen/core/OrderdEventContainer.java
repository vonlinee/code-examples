package io.maker.codegen.core;

import java.util.Queue;
import java.util.function.Predicate;

public class OrderdEventContainer extends EventContainer {

    transient volatile Queue<EventTask<Event>> eventQueue;

    @Override
    public void addListener(String name, EventHandler<? extends Event> handler) {
        
    }

    @Override
    public void removeListener(String name) {
        super.removeListener(name);
    }

    @Override
    public void publish(Event event) {
        super.publish(event);
    }

    @Override
    public <T> void publish(Event event, Predicate<T> filter) {
        super.publish(event, filter);
    }

    @Override
    public void fireEvent(String name) {
        super.fireEvent(name);
    }
}
