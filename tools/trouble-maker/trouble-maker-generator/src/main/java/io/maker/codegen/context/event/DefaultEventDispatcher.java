package io.maker.codegen.context.event;

public class DefaultEventDispatcher implements EventDispatcher {

	EventDispatchChain chain;
	
	public DefaultEventDispatcher(EventDispatchChain chain) {
		this.chain = chain;
	}
	
	@Override
	public ApplicationEvent dispatchEvent(ApplicationEvent event, EventDispatchChain tail) {
		return chain.dispatchEvent(event);
	}
}
