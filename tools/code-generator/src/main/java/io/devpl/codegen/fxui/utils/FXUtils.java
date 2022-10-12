package io.devpl.codegen.fxui.utils;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

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
     * 不要在 {@link javafx.fxml.Initializable#initialize(URL, ResourceBundle)} 方法中调用，获取的结果为 null
     * Initialize 处于加载FXML阶段，各个节点还未在Scene Graph中出现，其getParent返回null
     * @param node 节点
     * @return Stage，可能为null
     */
    public static Stage getOwnerStage(Node node) {
        Window window = getOwnerScene(node).getWindow();
        if (window instanceof Stage) {
            return ((Stage) window);
        }
        return null;
    }

    public static Scene getOwnerScene(Node parent) {
        Scene scene = null;
        while (scene == null && parent != null) {
            scene = parent.getScene();
            parent = parent.getParent();
        }
        return scene;
    }

    public static Stage createDialogStage(Node owner) {
        return createDialogStage("Stage", StageStyle.DECORATED, Modality.APPLICATION_MODAL, getOwnerStage(owner), null, false, false);
    }

    public static Stage createDialogStage(Window owner) {
        return createDialogStage("Stage", StageStyle.DECORATED, Modality.APPLICATION_MODAL, owner, null, false, false);
    }

    public static Stage createDialogStage(Window owner, Scene scene) {
        return createDialogStage("Stage", StageStyle.DECORATED, Modality.APPLICATION_MODAL, owner, scene, false, false);
    }

    public static Stage createDialogStage(
            String title, Window owner, Parent sceneRoot) {
        return createDialogStage(title, StageStyle.DECORATED, Modality.APPLICATION_MODAL, owner, new Scene(sceneRoot), false, false);
    }

    public static Stage createDialogStage(
            String title, Window owner, Scene scene) {
        return createDialogStage(title, StageStyle.DECORATED, Modality.APPLICATION_MODAL, owner, scene, false, false);
    }

    public static Stage createDialogStage(
            String title, Modality modality, Window owner, Scene scene) {
        return createDialogStage(title, StageStyle.DECORATED, modality, owner, scene, false, false);
    }

    public static Stage createDialogStage(
            String title, StageStyle style, Modality modality, Window owner, Scene scene) {
        return createDialogStage(title, style, modality, owner, scene, false, false);
    }

    public static Stage createDialogStage(
            String title, StageStyle style, Modality modality, Window owner, Scene scene, boolean maximized, boolean resizable) {
        Stage stage = new Stage();
        stage.initStyle(style);
        stage.initModality(modality);
        stage.initOwner(owner);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setMaximized(maximized);
        stage.setResizable(resizable);
        return stage;
    }
}
