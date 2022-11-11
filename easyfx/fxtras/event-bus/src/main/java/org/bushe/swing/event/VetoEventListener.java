package org.bushe.swing.event;

/**
 * Interface for classes that can veto class-based event publication from the {@link EventService}.
 *
 * @author Michael Bushe michael@bushe.com
 */
public interface VetoEventListener<T> {

    /**
     * Determine whether an event should be vetoed or published.
     * <p/>
     * The EventService calls this method <b>before</b> class-based publication of objects.  If any of the
     * VetoEventListeners return true, then none of the subscribers for that event are called. <p>Prerequisite:
     * VetoEventListener has to be subscribed with the EventService for the event object's class.</p> <p>Guaranteed to be
     * called in the SwingEventThread when using the SwingEventService (EventBus). See {@link EventService}</p>
     *
     * @param event The event object to veto or allow to be published.
     *
     * @return true if the event should be vetoed and not published, false if the event should be published.
     */
    boolean shouldVeto(T event);
}
