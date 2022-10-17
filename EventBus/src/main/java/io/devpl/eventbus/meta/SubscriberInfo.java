package io.devpl.eventbus.meta;

import io.devpl.eventbus.SubscriberMethod;

/**
 * Base class for generated index classes created by annotation processing.
 */
public interface SubscriberInfo {
    Class<?> getSubscriberClass();

    SubscriberMethod[] getSubscriberMethods();

    SubscriberInfo getSuperSubscriberInfo();

    boolean shouldCheckSuperclass();
}
