package com.panemu.tiwulfx.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventUtils {

    public static boolean isPrimaryKeyDoubleClicked(MouseEvent event) {
        return event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2;
    }
}
