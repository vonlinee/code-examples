package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.FXController;
import io.devpl.codegen.fxui.framework.ControllerEvent;
import javafx.event.Event;

import java.net.URL;
import java.util.ResourceBundle;

public class Test2 extends FXController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEventHandler(ControllerEvent.RECEIVE_DATA, event -> {
            System.out.println("事件接收者：Test2");
            System.out.println("EventType " + event.getEventType());
            System.out.println("Target " + event.getTarget());
            System.out.println("Source " + event.getSource());
        });

    }
}
