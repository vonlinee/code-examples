package io.maker.codegen.context.event;

import java.util.EventObject;

public abstract class Event extends EventObject implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Event(Object source) {
		super(source);
	}
	
	protected EventType<? extends Event> eventType;
	
	protected transient EventTarget target;
	
	protected boolean consumed;

}