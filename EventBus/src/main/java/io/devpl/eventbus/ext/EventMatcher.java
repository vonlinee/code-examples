package io.devpl.eventbus.ext;

import io.devpl.eventbus.Subscription;

/**
 * 发布订阅匹配过滤
 */
public interface EventMatcher {

    /**
     * 如果事件发布和事件订阅两者互相匹配，则发布的事件将被处理
     *
     * @param publication  事件发布信息
     * @param subscription 事件订阅信息
     * @return
     */
    boolean matches(Publication publication, Subscription subscription);
}
