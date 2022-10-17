package io.devpl.eventbus;

import lombok.Data;
import io.devpl.eventbus.ext.Invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Used internally by EventBus and generated subscriber indexes.
 */
@Data
public class SubscriberMethod implements Invocation<Object, Object> {
    final Method method;
    final ThreadMode threadMode;
    final Class<?> eventType;
    final int priority;
    final boolean sticky;

    /**
     * Used for efficient comparison
     */
    String methodString;
    private Object subscriber;

    String subscribedTopic;

    public SubscriberMethod(Method method, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.priority = priority;
        this.sticky = sticky;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof SubscriberMethod) {
            checkMethodString();
            SubscriberMethod otherSubscriberMethod = (SubscriberMethod) other;
            otherSubscriberMethod.checkMethodString();
            // Don't use method.equals because of http://code.google.com/p/android/issues/detail?id=7811#c6
            return methodString.equals(otherSubscriberMethod.methodString);
        } else {
            return false;
        }
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            methodString = method.getDeclaringClass().getName() +
                    '#' + method.getName() +
                    '(' + eventType.getName();
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public Object invoke(Object input) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(subscriber, input);
    }
}