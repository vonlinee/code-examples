package io.maker.codegen.context.event;

public interface EventSource {
	
	/**
	 * 
	 * @param <T>
	 * @param filter
	 */
	void addEventFilter(EventFilter<? extends ApplicationEvent> filter);
}
