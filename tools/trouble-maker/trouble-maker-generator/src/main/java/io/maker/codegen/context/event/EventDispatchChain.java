package io.maker.codegen.context.event;

/**
 * https://cloud.tencent.com/developer/ask/sof/175999
 */
public interface EventDispatchChain {
	
	/**
	 * 
	 * @param dispatcher
	 * @return
	 */
	EventDispatchChain append(EventDispatcher dispatcher);

	/**
	 * 
	 * @param dispatcher
	 * @return
	 */
	EventDispatchChain prepend(EventDispatcher dispatcher);

	/**
	 * 取出EventDispatcher
	 * @param applicationEvent
	 * @return
	 */
	ApplicationEvent dispatchEvent(ApplicationEvent applicationEvent);
}