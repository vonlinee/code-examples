package io.devpl.eventbus.fx;

import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;

public class Controller implements EventTarget {

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return null;
    }
}
