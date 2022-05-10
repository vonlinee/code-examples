package io.maker.codegen.context.event;

public interface EventTarget {
	EventDispatchChain buildEventDispatchChain(EventDispatchChain chain);
}