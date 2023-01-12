package io.devpl.toolkit.framework.mvc;

import javafx.event.Event;

public interface Subscription {

    <T extends Event> void invoke(T event);
}
