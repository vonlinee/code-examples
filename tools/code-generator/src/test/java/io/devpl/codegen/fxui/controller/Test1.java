package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.FXEvent;
import io.devpl.codegen.fxui.framework.FXController;
import io.devpl.codegen.fxui.framework.ControllerEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Test1 extends FXController {

    @FXML
    public Button btn1;
    @FXML
    public Button btn2;

    @FXML
    public void post1(MouseEvent mouseEvent) {
        // Button 向 Controller 发送事件
        // Event.fireEvent(this, new ControllerEvent(btn1, this, ControllerEvent.RECEIVE_DATA));
    }

    @FXML
    public void post2(MouseEvent mouseEvent) {
        // FXEvent.publish(btn2, new ControllerEvent(btn1, btn2, ControllerEvent.RECEIVE_DATA));
        this.fireEvent(null, ControllerEvent.RECEIVE_DATA);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addEventHandler(ControllerEvent.RECEIVE_DATA, event -> {
            System.out.println("事件接收者：Test1");
            System.out.println("EventType " + event.getEventType());
            System.out.println("Target " + event.getTarget());
            System.out.println("Source " + event.getSource());
        });
    }
}
