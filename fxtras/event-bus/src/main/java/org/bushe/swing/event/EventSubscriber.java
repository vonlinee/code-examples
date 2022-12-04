package org.bushe.swing.event;

import java.util.EventListener;

/**
 * 回调接口：适用于基于Class的订阅者
 * Callback interface for class-based subscribers of an {@link EventService}.
 * @param <T> 事件类型
 */
public interface EventSubscriber<T> extends EventListener {

    /**
     * Handle a published event. <p>The EventService calls this method on each publication of an object that matches the
     * class or interface passed to one of the EventService's class-based subscribe methods, specifically, {@link
     * EventService#subscribe(Class, EventSubscriber)} {@link EventService#subscribeExactly(Class, EventSubscriber)}
     * {@link EventService#subscribeStrongly(Class, EventSubscriber)} and {@link EventService#subscribeExactlyStrongly(Class,
     * EventSubscriber)}.
     * @param event The Object that is being published.
     */
    void onEvent(T event);
}