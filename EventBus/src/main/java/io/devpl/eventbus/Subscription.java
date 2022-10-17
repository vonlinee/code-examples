package io.devpl.eventbus;

import io.devpl.eventbus.ext.Invocation;

/**
 * 每个订阅的内容，包含订阅者信息，订阅逻辑
 */
public final class Subscription implements Comparable<Subscription> {
    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    final Invocation<Object, Object> invocation;
    /**
     * Becomes false as soon as {@link DefaultEventBus#unregister(Object)} is called, which is checked by queued event delivery
     * {@link DefaultEventBus#invokeSubscriber(PendingPost)} to prevent race conditions.
     */
    volatile boolean active;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
        subscriberMethod.setSubscriber(subscriber);
        this.invocation = subscriberMethod;
        active = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Subscription) {
            Subscription otherSubscription = (Subscription) other;
            return subscriber == otherSubscription.subscriber
                    && subscriberMethod.equals(otherSubscription.subscriberMethod);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return subscriber.hashCode() + subscriberMethod.methodString.hashCode();
    }

    public Object invokeSubscriber(Object event) throws Exception {
        return invocation.invoke(event);
    }

    @Override
    public int compareTo(Subscription o) {
        return 0;
    }
}