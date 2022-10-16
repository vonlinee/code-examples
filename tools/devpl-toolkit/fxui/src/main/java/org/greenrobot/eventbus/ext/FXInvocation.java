package org.greenrobot.eventbus.ext;

import javafx.event.Event;
import javafx.event.EventHandler;

public class FXInvocation<T extends Event> implements Invocation<T, Object> {

    private EventHandler<T> handler;

    @Override
    public Object invoke(T input) throws Exception {
        handler.handle(input);
        return null;
    }
}
