package io.devpl.codegen.fxui.eventbus;

import io.devpl.codegen.fxui.framework.EventBus;
import io.devpl.codegen.fxui.framework.FXEventBus;
import io.devpl.codegen.fxui.framework.Subscriber;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class FXEventBusTest {

    @Before
    public void localSetUp() {
        bus = new FXEventBus();
        calls = 0;
        threadID = Thread.currentThread().getId();
    }

    private EventBus bus;
    private int calls;
    private long threadID;

    @Test
    public void testFireEvent() {
        TestEvent e = new TestEvent(TestEvent.ANY, new Object());

        Subscriber subscriber = bus.addEventHandler(TestEvent.ANY, event -> {
            calls++;
            System.out.println(event.getSource());
            assertEquals("Handled event on a different thread", threadID, Thread.currentThread().getId());
            assertTrue("Received wrong event", e.getData() == event.getData()
                    && e.getEventType() == event.getEventType());
        });

        bus.fireEvent(e);

        // synchronous
        assertEquals(calls, 1);

        subscriber.unsubscribe();

        bus.fireEvent(e);

        assertEquals(calls, 1);
    }
}
