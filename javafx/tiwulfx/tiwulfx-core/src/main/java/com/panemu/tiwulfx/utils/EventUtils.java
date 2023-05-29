package com.panemu.tiwulfx.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventUtils {

    /**
     * 鼠标左键双击
     * @param event 事件
     * @return boolean
     */
    public static boolean isPrimaryKeyDoubleClicked(MouseEvent event) {
        return event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2;
    }
}
