package io.devpl.test;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventSubscriber;

/**
 * 
 * @since created on 2022年10月16日
 */
public class Main {

	public static void main(String[] args) {


		EventBus.subscribe(Main.class, new EventSubscriber<String>() {
			@Override
			public void onEvent(String event) {
				System.out.println(event);
			}
		});
		
		
		EventBus.publish("event");
		
	}
}
