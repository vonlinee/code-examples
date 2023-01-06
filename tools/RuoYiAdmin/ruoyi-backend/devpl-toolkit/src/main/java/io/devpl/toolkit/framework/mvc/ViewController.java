package io.devpl.toolkit.framework.mvc;

import javafx.event.EventDispatcher;
import javafx.event.EventTarget;

public interface ViewController extends EventTarget {

    /**
     * 事件传递
     * @return EventDispatcher
     */
    EventDispatcher getEventDispatcher();
}
