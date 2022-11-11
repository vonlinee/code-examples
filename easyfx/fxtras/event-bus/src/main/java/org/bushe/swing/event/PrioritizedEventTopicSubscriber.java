package org.bushe.swing.event;

/**
 * This is a convenience interface, particularly for inner classes, that implements
 * {@link EventTopicSubscriber} and {@link Prioritized}.
 */
public interface PrioritizedEventTopicSubscriber extends EventTopicSubscriber, Prioritized {
}