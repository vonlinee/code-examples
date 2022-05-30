package io.maker.codegen.context.event;

public interface EventTarget {
	
	/**
	 * 
	 * @param chain
	 * @return
	 */
	EventDispatchChain buildEventDispatchChain(EventDispatchChain chain);
}