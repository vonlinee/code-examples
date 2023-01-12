package org.greenrobot.eventbus;

public interface EventHandler<T> {

    void handle(T event);
}
