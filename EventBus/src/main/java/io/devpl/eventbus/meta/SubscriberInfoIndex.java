package io.devpl.eventbus.meta;

/**
 * Interface for generated indexes.
 */
public interface SubscriberInfoIndex {
    SubscriberInfo getSubscriberInfo(Class<?> subscriberClass);
}
