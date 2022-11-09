package io.fxsdk.mvc;

import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;

public abstract class FXController implements EventTarget {

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail;
    }
}
