package io.devpl.eventbus.meta;

import io.devpl.eventbus.SubscriberMethod;
import io.devpl.eventbus.EventBusException;
import io.devpl.eventbus.ThreadMode;

import java.lang.reflect.Method;

/**
 * Base class for generated subscriber meta info classes created by annotation processing.
 */
public abstract class AbstractSubscriberInfo implements SubscriberInfo {
    private final Class<?> subscriberClass;
    private final Class<? extends SubscriberInfo> superSubscriberInfoClass;
    private final boolean shouldCheckSuperclass;

    protected AbstractSubscriberInfo(Class<?> subscriberClass, Class<? extends SubscriberInfo> superSubscriberInfoClass,
                                     boolean shouldCheckSuperclass) {
        this.subscriberClass = subscriberClass;
        this.superSubscriberInfoClass = superSubscriberInfoClass;
        this.shouldCheckSuperclass = shouldCheckSuperclass;
    }

    @Override
    public Class<?> getSubscriberClass() {
        return subscriberClass;
    }

    @Override
    public SubscriberInfo getSuperSubscriberInfo() {
        if (superSubscriberInfoClass == null) {
            return null;
        }
        try {
            return superSubscriberInfoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldCheckSuperclass() {
        return shouldCheckSuperclass;
    }

    protected SubscriberMethod createSubscriberMethod(String methodName, Class<?> eventType) {
        return createSubscriberMethod(methodName, eventType, ThreadMode.POSTING, 0, false);
    }

    protected SubscriberMethod createSubscriberMethod(String methodName, Class<?> eventType, ThreadMode threadMode) {
        return createSubscriberMethod(methodName, eventType, threadMode, 0, false);
    }

    protected SubscriberMethod createSubscriberMethod(String methodName, Class<?> eventType, ThreadMode threadMode,
                                                      int priority, boolean sticky) {
        try {
            Method method = subscriberClass.getDeclaredMethod(methodName, eventType);
            return new SubscriberMethod(method, eventType, threadMode, priority, sticky);
        } catch (NoSuchMethodException e) {
            throw new EventBusException("Could not find subscriber method in " + subscriberClass +
                    ". Maybe a missing ProGuard rule?", e);
        }
    }

}
