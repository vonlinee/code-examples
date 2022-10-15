package org.greenrobot.eventbus.condition;

import org.greenrobot.eventbus.SubscriberMethod;

/**
 * 对事件进行过滤
 */
public interface Condition {

    String NONE = "io.devpl.eventbus.condition.NoneCondition";

    /**
     * <p>
     * 如果返回true，那么订阅者将和发布者发布者的发布事件这一行为匹配，从而响应该事件
     * <p>
     * 订阅者可以发布自己的条件，同时发布者也可以在发布事件时指定响应此事件的条件
     * 当发布者和订阅者的条件同时匹配，事件才能得到响应
     * @return
     */
    boolean matches(SubscriberMethod subscriber, Object publisher);
}
