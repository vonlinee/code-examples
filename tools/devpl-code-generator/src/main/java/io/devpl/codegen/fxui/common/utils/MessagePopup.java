package io.devpl.codegen.fxui.common.utils;

import javafx.scene.control.TextArea;
import javafx.stage.Popup;

/**
 * 消息弹窗，只有一个确定
 * 类似于Alert.information
 */
public final class MessagePopup extends Popup {

    private TextArea contentArea;

    public MessagePopup() {

    }
}
