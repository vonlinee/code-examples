package org.bushe.swing.event;

/**
 * Convenience base class for EventServiceEvents in the application. Provides
 * the convenience of holding the event source publication and event status. It
 * is not necessary to use this event class when using an EventService.
 * @author Michael Bushe michael@bushe.com
 */
public abstract class AbstractEventServiceEvent implements EventServiceEvent, PublicationStatusTracker {

	protected final Object stateLock = new Object();
	private Object source;
	private PublicationStatus publicationStatus = PublicationStatus.Unpublished;

	/**
	 * Default constructor
	 * @param source the source of the event
	 */
	public AbstractEventServiceEvent(Object source) {
		this.source = source;
	}

	/**
	 * @return the source of this event
	 */
	public Object getSource() {
		return source;
	}

	public PublicationStatus getPublicationStatus() {
		synchronized (stateLock) {
			return publicationStatus;
		}
	}

	public void setPublicationStatus(PublicationStatus status) {
		synchronized (stateLock) {
			publicationStatus = status;
		}
	}
}
