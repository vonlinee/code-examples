package io.maker.codegen.context.event;

/**
 * https://blog.csdn.net/THEONE10211024/article/details/77775489
 * 
 * https://www.demo2s.com/java/javafx-eventdispatcher-tutorial-with-examples.html
 * 
 * EventHandler的集合
 */
public interface EventDispatcher {
	
	/**
	 * 
	 * @param event	被分派的Event
	 * @param chain
	 * @return
	 */
	ApplicationEvent dispatchEvent(ApplicationEvent event, EventDispatchChain tail);
}