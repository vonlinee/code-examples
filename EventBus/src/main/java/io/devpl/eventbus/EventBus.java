package io.devpl.eventbus;

import java.util.function.Predicate;

public interface EventBus {

    String name();

    void publish(Object event);

    void publish(String topic, Object event);

    void publish(Object event, Predicate<Subscription> filter);

    void register(Object subscriber);

    void unregister(Object subscriber);
}
