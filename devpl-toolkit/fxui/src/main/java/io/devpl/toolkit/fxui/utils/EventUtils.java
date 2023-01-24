package io.devpl.toolkit.fxui.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventUtils {

    /**
     * 是否鼠标左键双击
     * @param event
     * @return
     */
    public static boolean isPrimaryButtonDoubleClicked(MouseEvent event) {
        return event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2;
    }
}
