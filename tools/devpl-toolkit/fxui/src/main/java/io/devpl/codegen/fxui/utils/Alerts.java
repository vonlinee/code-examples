package io.devpl.codegen.fxui.utils;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * 弹窗提示工具类
 */
public final class Alerts {

    private Alerts() {
    }

    // 弹窗默认宽高
    private static final double DEFAULT_ALERT_WIDTH = 500.0;
    private static final double DEFAULT_ALERT_HEIGHT = 400.0;
    private static final String DEFAULT_HEADER_TEXT = "标题";

    private static final String DEFAULT_INFO_WINDOW_TITLE = "信息";
    private static final String DEFAULT_WARN_WINDOW_TITLE = "警告";
    private static final String DEFAULT_ERROR_WINDOW_TITLE = "错误";
    private static final String DEFAULT_NONE_WINDOW_TITLE = "无消息";
    private static final String DEFAULT_CONFIRM_WINDOW_TITLE = "确认";

    /**
     * 展示信息
     * @param alertType  枚举类型
     * @param headerText 展示面板的标题
     * @param title      窗口(Stage)的标题
     * @param text       展示文本
     * @param pane       展示面板
     * @param resiable   是否可改变大小
     * @param w          宽度
     * @param h          高度
     */
    private static Alert build(Alert.AlertType alertType, String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setResizable(resiable);
        if (graphic != null) alert.setGraphic(graphic);
        if (pane != null) alert.setDialogPane(pane);
        alert.setWidth(w);
        alert.setHeight(h);
        return alert;
    }

    // ================= AlertType.INFO ================================

    public static Alert info(String text) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_INFO_WINDOW_TITLE, text);
    }

    public static Alert info(String message, boolean resiable) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_INFO_WINDOW_TITLE, message, resiable);
    }

    public static Alert info(String headerText, String text) {
        return info(headerText, "信息", text);
    }

    public static Alert info(String headerText, String title, String text) {
        return info(headerText, title, text, false);
    }

    public static Alert info(String headerText, String title, String text, boolean resiable) {
        return info(headerText, title, text, null, null, resiable);
    }

    public static Alert info(String headerText, String title, String text, DialogPane pane, Node graphic) {
        return info(headerText, title, text, pane, graphic, true, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert info(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable) {
        return info(headerText, title, text, pane, graphic, resiable, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert info(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        return build(Alert.AlertType.INFORMATION, headerText, title, text, pane, graphic, resiable, w, h);
    }

    // ================= AlertType.WARN ================================

    public static Alert warn(String text) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_WARN_WINDOW_TITLE, text);
    }

    public static Alert warn(String message, boolean resiable) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_WARN_WINDOW_TITLE, message, resiable);
    }

    public static Alert warn(String headerText, String text) {
        return info(headerText, DEFAULT_WARN_WINDOW_TITLE, text);
    }

    public static Alert warn(String headerText, String title, String text) {
        return info(headerText, title, text, false);
    }

    public static Alert warn(String headerText, String title, String text, boolean resiable) {
        return info(headerText, title, text, null, null, resiable);
    }

    public static Alert warn(String headerText, String title, String text, DialogPane pane, Node graphic) {
        return info(headerText, title, text, pane, graphic, true, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert warn(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable) {
        return info(headerText, title, text, pane, graphic, resiable, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert warn(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        return build(Alert.AlertType.INFORMATION, headerText, title, text, pane, graphic, resiable, w, h);
    }

    // ================= AlertType.ERROR ================================

    public static Alert error(String text) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_ERROR_WINDOW_TITLE, text);
    }

    public static Alert error(String message, boolean resiable) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_ERROR_WINDOW_TITLE, message, resiable);
    }

    public static Alert error(String headerText, String text) {
        return info(headerText, DEFAULT_ERROR_WINDOW_TITLE, text);
    }

    public static Alert error(String headerText, String title, String text) {
        return info(headerText, title, text, false);
    }

    public static Alert error(String headerText, String title, String text, boolean resiable) {
        return info(headerText, title, text, null, null, resiable);
    }

    public static Alert error(String headerText, String title, String text, DialogPane pane, Node graphic) {
        return info(headerText, title, text, pane, graphic, true, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert error(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable) {
        return info(headerText, title, text, pane, graphic, resiable, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert error(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        return build(Alert.AlertType.ERROR, headerText, title, text, pane, graphic, resiable, w, h);
    }

    // ================= AlertType.CONFIRMATION ================================

    public static Alert confirmation(String text) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_CONFIRM_WINDOW_TITLE, text);
    }

    public static Alert confirmation(String message, boolean resiable) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_CONFIRM_WINDOW_TITLE, message, resiable);
    }

    public static Alert confirmation(String headerText, String text) {
        return info(headerText, DEFAULT_CONFIRM_WINDOW_TITLE, text);
    }

    public static Alert confirmation(String headerText, String title, String text) {
        return info(headerText, title, text, false);
    }

    public static Alert confirmation(String headerText, String title, String text, boolean resiable) {
        return info(headerText, title, text, null, null, resiable);
    }

    public static Alert confirmation(String headerText, String title, String text, DialogPane pane, Node graphic) {
        return info(headerText, title, text, pane, graphic, true, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert confirmation(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable) {
        return info(headerText, title, text, pane, graphic, resiable, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert confirmation(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        return build(Alert.AlertType.CONFIRMATION, headerText, title, text, pane, graphic, resiable, w, h);
    }

    // ================= AlertType.NONE ================================

    public static Alert none(String text) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_NONE_WINDOW_TITLE, text);
    }

    public static Alert none(String message, boolean resiable) {
        return info(DEFAULT_HEADER_TEXT, DEFAULT_NONE_WINDOW_TITLE, message, resiable);
    }

    public static Alert none(String headerText, String text) {
        return info(headerText, DEFAULT_NONE_WINDOW_TITLE, text);
    }

    public static Alert none(String headerText, String title, String text) {
        return info(headerText, title, text, false);
    }

    public static Alert none(String headerText, String title, String text, boolean resiable) {
        return info(headerText, title, text, null, null, resiable);
    }

    public static Alert none(String headerText, String title, String text, DialogPane pane, Node graphic) {
        return info(headerText, title, text, pane, graphic, true, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert none(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable) {
        return info(headerText, title, text, pane, graphic, resiable, DEFAULT_ALERT_WIDTH, DEFAULT_ALERT_HEIGHT);
    }

    public static Alert none(String headerText, String title, String text, DialogPane pane, Node graphic, boolean resiable, double w, double h) {
        return build(Alert.AlertType.NONE, headerText, title, text, pane, graphic, resiable, w, h);
    }
}
