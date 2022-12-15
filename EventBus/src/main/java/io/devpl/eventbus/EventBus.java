package io.devpl.eventbus;

public interface EventBus {

    String name();

    void publish(Object event);

    void publish(String topic, Object event);

    void register(Object subscriber);

    void unregister(Object subscriber);
}
