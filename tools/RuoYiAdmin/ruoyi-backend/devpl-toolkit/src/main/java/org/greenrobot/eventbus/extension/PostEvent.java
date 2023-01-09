package org.greenrobot.eventbus.extension;

import lombok.Data;
import org.greenrobot.eventbus.EventBus;

import java.util.function.Consumer;

@Data
public final class PostEvent {

    private String eventName;
    private Object eventArg;
    private SubscriptionMatcher matcher;
    private Consumer<Object> consumer;

    // 只能用Object接收
    private volatile Object response;

    private final EventBus bus;

    public PostEvent(EventBus bus) {
        this.bus = bus;
    }

    public PostEvent matcher(SubscriptionMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> PostEvent callback(Consumer<T> consumer) {
        this.consumer = (Consumer<Object>) consumer;
        return this;
    }

    public void post() {
        bus.post(this);
    }
}
