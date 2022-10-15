package org.greenrobot.eventbus.condition;

import org.greenrobot.eventbus.SubscriberMethod;

public class NoneCondition implements Condition {
    @Override
    public boolean matches(SubscriberMethod subscriber, Object publisher) {
        return true;
    }
}
