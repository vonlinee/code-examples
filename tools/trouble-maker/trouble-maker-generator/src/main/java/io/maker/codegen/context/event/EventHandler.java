package io.maker.codegen.context.event;

import java.util.EventListener;

/**
 * 一个ApplicationEvent对应多个EventHandler
 *
 * @param <T>
 */
public interface EventHandler<T extends ApplicationEvent> extends EventListener {
	void handle(T event);
}