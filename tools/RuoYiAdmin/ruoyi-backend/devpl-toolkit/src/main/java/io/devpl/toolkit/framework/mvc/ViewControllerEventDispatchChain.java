package io.devpl.toolkit.framework.mvc;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

/**
 * 视图控制器之间数据传递
 */
public class ViewControllerEventDispatchChain implements EventDispatchChain {

    @Override
    public EventDispatchChain append(EventDispatcher eventDispatcher) {
        return null;
    }

    @Override
    public EventDispatchChain prepend(EventDispatcher eventDispatcher) {
        return null;
    }

    @Override
    public Event dispatchEvent(Event event) {
        return null;
    }
}
