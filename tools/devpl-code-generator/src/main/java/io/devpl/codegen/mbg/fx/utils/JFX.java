package io.devpl.codegen.mbg.fx.utils;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * 便捷的方法用于创建JavaFX控件
 * 类似于SWT
 */
public final class JFX {

    /**
     * 工具类
     */
    public static final StringConverter<String> DEFAULT_STRING_CONVERTER = new DefaultStringConverter();

    private JFX() {
    }

    public static Button newButton(String text) {
        Button button = new Button();
        button.setText(text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        return button;
    }

    public static Button newButton(String text, EventHandler<? super MouseEvent> value) {
        Button button = new Button();
        button.setText(text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        button.setOnMouseClicked(value);
        return button;
    }

    public static Button newButton(String text, Node graph) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(true);
        return button;
    }

    public static Button newButton(String text, Node graph, boolean defaultButton) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setDefaultButton(defaultButton);
        return button;
    }

    public static Button newButton(String text, Node graph, Pos alignment, boolean defaultButton) {
        Button button = new Button();
        button.setText(text);
        button.setGraphic(graph);
        button.setAlignment(alignment);
        button.setDefaultButton(defaultButton);
        return button;
    }

    /**
     * 给控件添加一个按钮
     * @param group
     * @param text
     */
    public static Button addButton(Group group, String text, EventHandler<? super MouseEvent> value) {
        Button btn = newButton(text, value);
        group.getChildren().add(btn);
        return btn;
    }

    /**
     * 给控件添加一个按钮
     * @param pane
     * @param text
     */
    public static Button addButton(Pane pane, String text, EventHandler<? super MouseEvent> value) {
        Button btn = newButton(text, value);
        pane.getChildren().add(btn);
        return btn;
    }

    /**
     * 加载图片
     * @param pathname 相对路径
     * @param w        宽度
     * @param h        高度
     * @return ImageView
     */
    public static ImageView loadImageView(String pathname, double w, double h) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        return dbImage;
    }

    public static ImageView loadImageView(String pathname, double w, double h, Object userData) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        dbImage.setUserData(userData);
        return dbImage;
    }

    public static void setTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }
}
