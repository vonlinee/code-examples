package org.greenrobot.eventbus;

import java.lang.reflect.InvocationTargetException;

class MethodSubscription implements Subscription {

    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    /**
     * Becomes false as soon as {@link EventBus#unregister(Object)} is called, which is checked by queued event delivery
     * {@link EventBus#invokeSubscriber(PendingPost)} to prevent race conditions.
     */
    volatile boolean active;

    MethodSubscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
        active = true;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MethodSubscription) {
            MethodSubscription otherSubscription = (MethodSubscription) other;
            return subscriber == otherSubscription.subscriber && subscriberMethod.equals(otherSubscription.subscriberMethod);
        } else {
            return false;
        }
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public int hashCode() {
        return subscriber.hashCode() + subscriberMethod.methodString.hashCode();
    }

    @Override
    public Object invokeSubscriber(Object... args) throws InvocationTargetException, IllegalAccessException {
        return subscriberMethod.method.invoke(subscriber, args);
    }

    @Override
    public ThreadMode getThredMode() {
        return subscriberMethod.threadMode;
    }

    @Override
    public int getPriority() {
        return subscriberMethod.priority;
    }
}
