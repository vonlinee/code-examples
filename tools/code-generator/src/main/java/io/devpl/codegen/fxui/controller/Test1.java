package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.FXControllerBase;
import io.devpl.codegen.fxui.framework.MessageEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Test1 extends FXControllerBase {

    @FXML
    public Button btn1;
    @FXML
    public Button btn2;

    @FXML
    public void post1(MouseEvent mouseEvent) {
        // Button 向 Controller 发送事件
        Event.fireEvent(this, new MessageEvent(btn1, this, MessageEvent.SEND_DATA));
    }

    @FXML
    public void post2(MouseEvent mouseEvent) {
        // Controller 向 Button 发送事件
        MessageEvent event = new MessageEvent(this, btn2, MessageEvent.SEND_DATA);
        Event.fireEvent(btn2, event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn2.addEventHandler(MessageEvent.SEND_DATA, event -> {
            System.out.println("Controller 向 Button 发送事件");
            System.out.println(event.getEventType());
            System.out.println(event.getTarget());
            System.out.println(event.getSource());
        });
        addEventHandler(MessageEvent.SEND_DATA, event -> {
            System.out.println("Button 向 Controller 发送事件");
            System.out.println(event.getEventType());
            System.out.println(event.getTarget());
            System.out.println(event.getSource());
        });
    }
}
