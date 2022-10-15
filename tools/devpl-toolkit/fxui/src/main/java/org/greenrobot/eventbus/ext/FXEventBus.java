package org.greenrobot.eventbus.ext;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class FXEventBus {

    public <T extends Event> void post(T event) {

    }

    public <T extends Event> void register(EventType<T> eventType, EventHandler<T> handler) {

    }
}
