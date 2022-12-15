package io.devpl.eventbus.ext;

import io.devpl.eventbus.Subscription;

/**
 * 订阅匹配过滤
 */
public interface SubscriptionMatcher {
    boolean matches(Subscription subscription);
}
