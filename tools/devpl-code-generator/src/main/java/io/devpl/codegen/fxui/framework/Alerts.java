package io.devpl.codegen.fxui.framework;

import javafx.scene.control.Alert;

/**
 * FX弹窗警告
 */
public final class Alerts {

    private Alerts() {
    }

    private static final Alert INFO = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert WARN = new Alert(Alert.AlertType.WARNING);
    private static final Alert ERROR = new Alert(Alert.AlertType.ERROR);
    private static final Alert NONE = new Alert(Alert.AlertType.NONE);
    private static final Alert CONFIRM = new Alert(Alert.AlertType.CONFIRMATION);

    public static Alert info(String message) {
        INFO.setContentText(message);
        return INFO;
    }

    public static Alert warn(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        return alert;
    }

    public static Alert error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setWidth(400);
        alert.setHeight(400);
        alert.setResizable(true);
        alert.setContentText(message);
        return alert;
    }

    /**
     * 创建Alert对象
     * @param alertType   Alert.AlertType
     * @param contentText 文本
     * @param width       宽度
     * @param height      高度
     * @param resizable   是否可改变大小
     * @return Alert实例
     */
    public static Alert newAlert(Alert.AlertType alertType, String contentText, double width, double height, boolean resizable) {
        final Alert alert = new Alert(alertType);
        alert.setResizable(resizable);
        alert.setWidth(width);
        alert.setHeight(height);
        alert.setContentText(contentText);
        return alert;
    }

    public static Alert confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        return alert;
    }

    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public static void showWarnAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * build both OK and Cancel buttons for the user to click on to dismiss the
     * dialog.
     * @param message
     */
    public static Alert confirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        return alert;
    }
}
