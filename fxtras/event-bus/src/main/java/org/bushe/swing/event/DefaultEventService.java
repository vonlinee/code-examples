package org.bushe.swing.event;

public class DefaultEventService extends ThreadSafeEventService {

    /**
     * By default, the SwingEventService is constructed such that any listener that takes over 200 ms causes an
     * SubscriberTimingEvent to be published.  You will need to add a subscriber to this event.  Note that if you use
     * event to launch a modal dialog, the timings will be as long as the dialog is up - this is the way Swing works.
     */
    public DefaultEventService() {
        super((long) 200, false, null, null, null);
    }

    public DefaultEventService(Long timeThresholdForEventTimingEventPublication) {
        super(timeThresholdForEventTimingEventPublication, false, null, null, null);
    }

    /**
     * Create a SwingEventService is such that any listener that takes over timeThresholdForEventTimingEventPublication
     * milliseconds causes an EventSubscriberTimingEvent to be published.  You can add a subscriber to this event or set
     * subscribeTimingEventsInternally to true to cause the default logging to occur through the protected {@link
     * #subscribeTiming(SubscriberTimingEvent)} call.
     * <p/>
     * Note that if you use event to launch a modal dialog, the timings will be as long as the dialog is up - this is the
     * way Swing works.
     * @param timeThresholdForEventTimingEventPublication the longest time a subscriber should spend handling an event,
     *                                                    The service will publish an SubscriberTimingEvent after listener processing if the time was exceeded.  If null, no
     *                                                    SubscriberTimingEvent will be issued.
     * @param subscribeTimingEventsInternally             add a subscriber to the EventSubscriberTimingEvent internally and call the
     *                                                    protected {@link #subscribeTiming(SubscriberTimingEvent)} method when they occur.  This logs a warning to the
     *                                                    {@link Logger} logger by default.
     * @throws IllegalArgumentException if timeThresholdForEventTimingEventPublication is null and
     *                                  subscribeTimingEventsInternally is true.
     */
    public DefaultEventService(Long timeThresholdForEventTimingEventPublication, boolean subscribeTimingEventsInternally) {
        super(timeThresholdForEventTimingEventPublication, subscribeTimingEventsInternally, null, null, null);
    }
}
