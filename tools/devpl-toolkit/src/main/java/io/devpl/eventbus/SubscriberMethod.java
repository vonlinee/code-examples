package io.devpl.eventbus;

import java.lang.reflect.Method;

/**
 * 仅内部使用
 * Used internally by EventBus and generated subscriber indexes.
 */
public class SubscriberMethod {
    private final Method method;
    private final ThreadMode threadMode;
    private final Class<?> eventType;
    private final int priority;
    private final boolean sticky;
    private final Class<?> returnType;

    /**
     * Used for efficient comparison
     */
    String methodString;

    public SubscriberMethod(Method method, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.priority = priority;
        this.sticky = sticky;
        returnType = method.getReturnType();
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
        }
        return false;
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            methodString = method.getDeclaringClass().getName() +
                    '#' + method.getName() +
                    '(' + eventType.getName();
        }
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isSticky() {
        return sticky;
    }

    public String getMethodString() {
        return methodString;
    }
}