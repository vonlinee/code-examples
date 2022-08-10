package io.maker.codegen.context.event.test;

import io.maker.codegen.context.event.EventDispatchChain;
import io.maker.codegen.context.event.EventTarget;

public class Target implements EventTarget {

	public static void main(String[] args) {
		Target target = new Target();
	}

	@Override
	public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
		return null;
	}
}
