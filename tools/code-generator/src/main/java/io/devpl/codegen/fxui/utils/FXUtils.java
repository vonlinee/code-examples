package io.devpl.codegen.fxui.utils;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * 提供一些模板方法
 */
public abstract class FXUtils {

    /**
     * 加载图片
     * @param pathname
     * @param w
     * @param h
     * @return
     */
    public static ImageView loadImageView(String pathname, double w, double h) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(w);
        dbImage.setFitWidth(h);
        return dbImage;
    }

    public static ImageView loadImageView(String pathname, double w, double h, Object userData) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(w);
        dbImage.setFitWidth(h);
        dbImage.setUserData(userData);
        return dbImage;
    }

    public static void setControlTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }

    /**
     * 不对入参进行校验
     * @param textField
     * @param content
     */
    public static void setTextIfEmpty(TextField textField, String content) {
        String text = textField.getText();
        if (text == null || text.isEmpty()) textField.setText(content);
    }

    public static <T> void setValueIfEmpty(ChoiceBox<T> choiceBox, T value) {
        if (choiceBox.getValue() == null) {
            choiceBox.setValue(value);
        }
    }

    /**
     * 关闭Node所在的父窗口
     * @param node
     */
    public static boolean closeOwnerStage(Node node) {
        Window window = node.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
            return true;
        }
        return false;
    }

    /**
     * 关闭Node所在的父窗口
     * @param node
     */
    public static boolean showOwnerStage(Node node) {
        Window window = node.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).show();
            return true;
        }
        return false;
    }

    /**
     * 关闭Node所在的父窗口
     * @param node
     */
    public static boolean setSceneOfOwnerStage(Node node, Scene scene) {
        Window window = node.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).setScene(scene);
            return true;
        }
        return false;
    }

    /**
     * 获取该控件所在的Stage
     * @param node 节点
     * @return Stage，nullable
     */
    public static Stage getOwnerStage(Node node) {
        Window window = node.getScene().getWindow();
        if (window instanceof Stage) {
            return ((Stage) window);
        }
        return null;
    }
}
