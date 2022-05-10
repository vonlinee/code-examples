package io.maker.codegen.context.event;

public interface EventFilter<T extends Event> {
	void doFilter();
}
