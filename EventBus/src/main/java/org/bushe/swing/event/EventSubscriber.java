package org.bushe.swing.event;

/**
 * Callback interface for class-based subscribers of an {@link EventService}.
 *
 * @author Michael Bushe michael@bushe.com
 */
public interface EventSubscriber<T> {

	/**
	 * Handle a published event.
	 * <p>
	 * The EventService calls this method on each publication of an object that
	 * matches the class or interface passed to one of the EventService's
	 * class-based subscribe methods, specifically,
	 * {@link EventService#subscribe(Class,EventSubscriber)}
	 * {@link EventService#subscribeExactly(Class,EventSubscriber)}
	 * {@link EventService#subscribeStrongly(Class,EventSubscriber)} and
	 * {@link EventService#subscribeExactlyStrongly(Class, EventSubscriber)}.
	 *
	 * @param event The Object that is being published.
	 */
	public void onEvent(T event);
}
