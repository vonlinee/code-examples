package io.maker.codegen.context.event;

public interface EventDispatcher {
	Event dispatchEvent(Event var1, EventDispatchChain var2);
}