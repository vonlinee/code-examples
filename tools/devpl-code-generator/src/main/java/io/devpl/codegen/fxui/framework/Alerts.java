package io.devpl.codegen.fxui.framework;

import javafx.scene.control.Alert;

/**
 * FX弹窗警告
 */
public final class Alerts {

    private Alerts() {}

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
        alert.setContentText(message);
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
