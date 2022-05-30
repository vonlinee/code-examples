package io.maker.codegen.context.event;

import java.util.EventObject;

/**
 * 事件顶层抽象类
 */
public abstract class ApplicationEvent extends EventObject implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ApplicationEvent(Object source) {
		super(source);
	}
	
	protected EventType<? extends ApplicationEvent> eventType;
	
	protected transient EventTarget target;
	
	protected abstract void onConsumed();
	
	protected abstract boolean isConsumed();
}