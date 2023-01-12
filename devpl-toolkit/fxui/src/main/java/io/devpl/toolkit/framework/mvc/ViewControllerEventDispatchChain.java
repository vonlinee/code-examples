package io.devpl.toolkit.framework.mvc;

import com.sun.javafx.event.EventDispatchChainImpl;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

/**
 * 视图控制器之间数据传递
 */
public class ViewControllerEventDispatchChain implements EventDispatchChain {

    private final EventDispatchChainImpl chainImpl;

    public ViewControllerEventDispatchChain() {
        this.chainImpl = new EventDispatchChainImpl();
    }

    @Override
    public EventDispatchChain append(EventDispatcher eventDispatcher) {
        return chainImpl.append(eventDispatcher);
    }

    @Override
    public EventDispatchChain prepend(EventDispatcher eventDispatcher) {
        return chainImpl.prepend(eventDispatcher);
    }

    @Override
    public Event dispatchEvent(Event event) {
        return chainImpl.dispatchEvent(event);
    }
}
