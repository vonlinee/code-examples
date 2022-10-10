package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.FXControllerBase;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Test2 extends FXControllerBase implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("初始化");
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(location);
                System.out.println(resources);
                System.out.println(event);
            }
        });
    }
}
