package org.greenrobot.eventbus.extension;

import org.greenrobot.eventbus.SubscriberMethod;

@FunctionalInterface
public interface SubscriptionMatcher {

    boolean matches(Object subscriber, SubscriberMethod subscriberMethod);

    SubscriptionMatcher TRUE = TrueSubscriptionMatcher.INSTANCE;
}
