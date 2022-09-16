package io.devpl.codegen.mbg.fx.utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 * 便捷的方法用于创建JavaFX控件
 */
public final class FXControls {

    private FXControls() {
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
     * 加载图片
     * @param pathname
     * @param w
     * @param h
     * @return
     */
    public static ImageView loadImageView(String pathname, double w, double h) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        return dbImage;
    }

    public static void setTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }
}
