package io.devpl.eventbus;

import io.devpl.eventbus.ext.Callback;
import io.devpl.eventbus.ext.Invocation;

import java.lang.reflect.Method;

/**
 * 封装订阅者信息
 * 包含订阅者所在类，订阅方法
 */
public class Subscription implements Invocation<Object, Object> {

    volatile boolean consumed;

    private final Object subscriber;
    private final SubscriberMethod subscriberMethod;

    /**
     * 可能为Null
     */
    private Callback callback;

    /**
     * Becomes false as soon as {@link DefaultEventBus#unregister(Object)} is called, which is checked by queued event delivery
     * { EventBus#invokeSubscriber(PendingPost)} to prevent race conditions.
     */
    volatile boolean active;

    Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
        active = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Subscription) {
            Subscription otherSubscription = (Subscription) other;
            return subscriber == otherSubscription.subscriber
                    && subscriberMethod.equals(otherSubscription.subscriberMethod);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return subscriber.hashCode() + subscriberMethod.methodString.hashCode();
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public SubscriberMethod getSubscriberMethod() {
        return subscriberMethod;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Object invoke(Object input) throws Exception {
        Method method = subscriberMethod.getMethod();
        method.setAccessible(true);
        Object result = method.invoke(subscriber, input);
        if (callback != null) {
            callback.call(result);
        }
        return result;
    }
}