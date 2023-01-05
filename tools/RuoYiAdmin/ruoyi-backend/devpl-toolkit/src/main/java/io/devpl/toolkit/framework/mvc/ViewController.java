package io.devpl.toolkit.framework.mvc;

import org.greenrobot.eventbus.EventBus;

public interface ViewController {

    default void publish(Object event) {
        EventBus.getDefault().post(event);
    }
}
