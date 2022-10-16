package org.greenrobot.eventbus.meta;

/**
 * Interface for generated indexes.
 * 生成索引
 */
public interface SubscriberInfoIndex {
    SubscriberInfo getSubscriberInfo(Class<?> subscriberClass);
}
