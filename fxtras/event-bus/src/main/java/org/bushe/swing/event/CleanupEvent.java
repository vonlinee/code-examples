package org.bushe.swing.event;

/**
 * Published when the ThreadSafeEventService cleans up stale subscribers.
 * @author Michael Bushe
 */
public class CleanupEvent {

    private Status status;
    private int totalWeakRefsAndProxies;
    private Integer numStaleSubscribersCleaned;

    public CleanupEvent(Status status, int totalWeakRefsAndProxies, Integer numStaleSubscribersCleaned) {
        this.status = status;
        this.totalWeakRefsAndProxies = totalWeakRefsAndProxies;
        this.numStaleSubscribersCleaned = numStaleSubscribersCleaned;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Total weak refs and ProxySubscribers subscribed.
     */
    public int getTotalWeakRefsAndProxies() {
        return totalWeakRefsAndProxies;
    }

    /**
     * Null unless status is FINISHED_CLEANING.
     * @return the number of stale subscribers cleaned during the cleanup run.
     */
    public Integer getNumStaleSubscribersCleaned() {
        return numStaleSubscribersCleaned;
    }

    /**
     * The status of the cleanup.
     */
    public enum Status {
        /**
         * Timer has started the cleanup task. Will be followed by at least one more CleanupEvent.
         */
        STARTING,
        /**
         * Task has determined there's cleanup to do.
         */
        OVER_STOP_THRESHOLD_CLEANING_BEGUN,
        /**
         * Task has determined there's no cleanup to do.
         */
        UNDER_STOP_THRESHOLD_CLEANING_CANCELLED,
        /**
         * Finished cleaning up task.
         */
        FINISHED_CLEANING;
    }
}
