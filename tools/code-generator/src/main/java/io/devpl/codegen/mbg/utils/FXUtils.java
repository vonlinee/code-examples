package io.devpl.codegen.mbg.utils;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public abstract class FXUtils {

    /**
     * 加载图片
     *
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

    public static void setControlTooltip(Control control, String tipText) {
        control.setTooltip(new Tooltip(tipText));
    }
}
