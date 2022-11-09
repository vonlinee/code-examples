package io.fxsdk.app;

import com.sun.javafx.event.CompositeEventHandler;
import com.sun.javafx.event.EventHandlerManager;
import com.sun.javafx.scene.NodeEventDispatcher;
import io.fxsdk.eventbus.FXEvent;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button button = new Button("Button");

        Method method = Node.class.getDeclaredMethod("getInternalEventDispatcher");

        button.addEventHandler(FXEvent.ANY, event -> {
            System.out.println("FXEvent111");
        });
        button.addEventHandler(FXEvent.ANY, event -> {
            System.out.println("FXEvent222");
        });
        button.addEventHandler(FXEvent.ANY, event -> {
            System.out.println("FXEvent333");
        });

        button.setOnMouseClicked(event -> {
            try {
                method.setAccessible(true);
                NodeEventDispatcher dispatcher = (NodeEventDispatcher) method.invoke(button);

                EventHandlerManager manager = dispatcher.getEventHandlerManager();

                Field field = EventHandlerManager.class.getDeclaredField("eventHandlerMap");
                field.setAccessible(true);
                Map<EventType<? extends Event>, CompositeEventHandler<? extends Event>> map = Collections.unmodifiableMap((Map<EventType<? extends Event>, CompositeEventHandler<? extends Event>>) field.get(manager));

                map.entrySet().forEach(System.out::println);

            } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            Event.fireEvent(button, new FXEvent());
        });
        button.setOnAction(System.out::println);

        Group group = new Group(button);
        Scene scene = new Scene(group, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
