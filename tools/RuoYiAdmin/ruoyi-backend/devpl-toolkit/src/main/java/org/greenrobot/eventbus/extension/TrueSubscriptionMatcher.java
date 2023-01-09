package org.greenrobot.eventbus.extension;

import org.greenrobot.eventbus.SubscriberMethod;

public class TrueSubscriptionMatcher implements SubscriptionMatcher {

    public static final TrueSubscriptionMatcher INSTANCE = new TrueSubscriptionMatcher();

    @Override
    public boolean matches(Object subscriber, SubscriberMethod subscriberMethod) {
        return true;
    }
}
