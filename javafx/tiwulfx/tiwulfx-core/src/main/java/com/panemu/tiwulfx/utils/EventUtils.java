package com.panemu.tiwulfx.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventUtils {

    public static boolean isPrimaryDoubleClikced(MouseEvent mouseEvent) {
        return mouseEvent.getButton()
                .equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2;
    }
}
