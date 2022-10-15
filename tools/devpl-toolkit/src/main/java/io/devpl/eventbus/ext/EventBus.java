package io.devpl.eventbus.ext;

import io.devpl.eventbus.DefaultEventBus;

public interface EventBus {

    void post(Object event);

    void register(Object subscriber);

    boolean isRegistered(Object subscriber);

    void unregister(Object subscriber);

    /**
     * Convenience singleton for apps using a process-wide EventBus instance.
     */
    static EventBus getDefault() {
        return DefaultEventBus.getDefault();
    }
}
