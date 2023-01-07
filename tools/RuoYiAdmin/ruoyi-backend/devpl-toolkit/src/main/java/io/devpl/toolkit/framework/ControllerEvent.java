package io.devpl.toolkit.framework;

import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器间数据传递
 */
public class ControllerEvent extends ActionEvent {

    private Map<String, Object> data;

    private Map<String, Object> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }
}
