package io.devpl.eventbus.event;

import java.util.EventObject;

public class Intent extends EventObject implements Cloneable {

    private static final long serialVersionUID = 20121107L;

    /**
     * Whether this event has been consumed by any filter or handler.
     */
    protected boolean consumed;

    /**
     * Constructs a prototypical Event.
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    protected Intent(Object source) {
        super(source);
    }

    public void consume() {
        this.consumed = true;
    }

    @Override
    protected Intent clone() throws CloneNotSupportedException {
        Intent newEvent = (Intent) super.clone();
        newEvent.source = this.source;
        newEvent.consumed = false;
        return newEvent;
    }
}
