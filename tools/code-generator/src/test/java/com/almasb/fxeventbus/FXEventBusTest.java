package com.almasb.fxeventbus;

import io.devpl.codegen.fxui.framework.EventBus;
import io.devpl.codegen.fxui.framework.FXEventBus;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import org.junit.Test;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class FXEventBusTest {

    @Test
    public void testFireEvent() {
        EventBus bus = new FXEventBus();

        EventType<Event> type = new EventType<>("mvc");

        bus.addEventHandler(type, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(event);
            }
        });
        bus.fireEvent(new Event(type));
    }
}
