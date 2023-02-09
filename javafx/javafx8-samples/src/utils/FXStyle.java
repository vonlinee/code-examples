package utils;

import javafx.scene.paint.Color;

public class FXStyle {

    /**
     * 背景色
     *
     * @param colorCode 例如:#8B008B
     * @return 背景色
     */
    public static String backgroundColor(String colorCode) {
        return "-fx-background-color: " + colorCode;
    }

    public static String backgroundColor(Color color) {
        int r = (int) Math.round(color.getRed() * 255.0);
        int g = (int) Math.round(color.getGreen() * 255.0);
        int b = (int) Math.round(color.getBlue() * 255.0);
        // int o = (int)Math.round(opacity * 255.0);
        // X 表示以十六进制形式输出 02 表示不足两位，前面补0输出；
        return "-fx-background-color: " + String.format("#%02x%02x%02x", r, g, b);
    }
}
