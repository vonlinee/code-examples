package io.devpl.eventbus;

import java.util.List;

/**
 * we could provide a callback to post() to be notified, an alternative would be events, of course...
 * 回调也可以直接通过定义的事件类完成
 */
public interface PostCallback {
    void onPostCompleted(List<SubscriberExceptionEvent> exceptionEvents);
}