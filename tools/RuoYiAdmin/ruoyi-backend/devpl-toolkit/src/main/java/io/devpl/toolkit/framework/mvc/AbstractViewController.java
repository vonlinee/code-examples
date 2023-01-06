package io.devpl.toolkit.framework.mvc;

import javafx.event.EventDispatchChain;

public abstract class AbstractViewController implements ViewController {

    private static final EventDispatchChain viewControllerDispatcherChain
            = new ViewControllerEventDispatchChain();

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return viewControllerDispatcherChain;
    }
}
