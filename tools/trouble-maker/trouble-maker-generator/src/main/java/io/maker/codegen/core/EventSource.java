package io.maker.codegen.core;

import java.util.function.Predicate;

/**
 * 事件源不需要实现或继承任何接口或类，它是事件最初发生的地方。因为事件源需要注册事件监听器，所以事件源内需要有相应的盛放事件监听器的容器
 */
public interface EventSource {

    void addListener(String name, EventHandler<? extends Event> listener);

    void removeListener(String name);

    <T> void publish(Event event, Predicate<T> filter);

    <T> void publish(Event event);

    void fireEvent(String name);

    void cancel(String name);
}
