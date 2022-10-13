package io.devpl.eventbus.ext;

import javafx.event.Event;
import javafx.event.EventHandler;

public class FXSubscriberMethod<T extends Event> implements SubscriberMethod {

    EventHandler<T> handler;

    @Override
    public Object invoke() {
        return null;
    }
}
