package io.fxtras.sdk.test;

import io.fxtras.sdk.mvc.FXMLPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.bushe.swing.event.EventBus;

import java.util.HashMap;

public class Controller extends FXMLPaneController {

    @FXML
    public Button btn;

    @Override
    protected void initialize() {
        EventBus.subscribe("topic", (topic, data) -> {
            System.out.println(topic);
            System.out.println(data);
        });
    }

    @FXML
    public void click(ActionEvent actionEvent) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", this);
        map.put("event", actionEvent);
        EventBus.publish("topic", map);
    }
}
