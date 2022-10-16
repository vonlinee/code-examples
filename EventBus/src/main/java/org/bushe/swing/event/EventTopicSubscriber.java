package org.bushe.swing.event;

/**
 * Callback interface for topic-based subscribers of an {@link EventService}.
 *
 * @author Michael Bushe michael@bushe.com
 */
public interface EventTopicSubscriber<T> {

   /**
    * Handle an event published on a topic.
    * <p/>
    * The EventService calls this method on each publication on a matching topic name passed to one of the
    * EventService's topic-based subscribe methods, specifically, {@link EventService#subscribe(String,
    *EventTopicSubscriber)} {@link EventService#subscribe(java.util.regex.Pattern,EventTopicSubscriber)} {@link
    * EventService#subscribeStrongly(String,EventTopicSubscriber)} and {@link EventService#subscribeStrongly(java.util.regex.Pattern,
    *EventTopicSubscriber)}.
    *
    * @param topic the name of the topic published on
    * @param data the data object published on the topic
    */
   public void onEvent(String topic, T data);
}
