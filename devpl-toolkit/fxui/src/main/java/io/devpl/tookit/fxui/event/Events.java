package io.devpl.tookit.fxui.event;

import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Events {

    /**
     * 初始化数据绑定
     */
    public static final String INIT_DATA_BINDER = "InitDataBind";
    public static final String FILL_CONNECTION_INFO = "Event-FillConnectionInfo";
    public static final String ADD_NEW_CONNECTION = "add-new-connection";

    /**
     * 是否鼠标左键双击
     * @param event
     * @return
     */
    public static boolean isPrimaryButtonDoubleClicked(MouseEvent event) {
        return event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getEventSource(Event event, Class<T> sourceType) {
        return (T) event.getSource();
    }
}
