package io.devpl.eventbus.ext;

import io.devpl.eventbus.Subscription;

@FunctionalInterface
public interface SubscriptionFilter {
    boolean doFilter(Subscription subscription);
}
