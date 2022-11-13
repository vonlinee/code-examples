package org.bushe.swing.event;

public interface CallableEventSubscriber<T, R> extends EventSubscriber<T> {

    R get();

    @Override
    void onEvent(T event);
}
