package io.devpl.codegen.mbg.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

public abstract class FXUtils {

    public static Button newButton() {
        Button button = new Button();

        return button;
    }

    /**
     * 加载图片
     * @param pathname
     * @param w
     * @param h
     * @return
     */
    @NotNull
    public static ImageView loadImageView(String pathname, double w, double h) {
        ImageView dbImage = new ImageView(pathname);
        dbImage.setFitHeight(h);
        dbImage.setFitWidth(w);
        return dbImage;
    }

    public static void setControlTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }
}
