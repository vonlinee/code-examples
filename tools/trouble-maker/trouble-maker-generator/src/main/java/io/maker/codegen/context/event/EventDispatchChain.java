package io.maker.codegen.context.event;

public interface EventDispatchChain {
	
	EventDispatchChain append(EventDispatcher dispatcher);

	EventDispatchChain prepend(EventDispatcher dispatcher);

	Event dispatchEvent(Event event);
}