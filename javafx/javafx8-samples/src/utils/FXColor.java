package utils;

import javafx.scene.paint.Color;

/**
 * 颜色代码：<a href="https://www.sioe.cn/yingyong/yanse-rgb-16/">...</a>
 */
public class FXColor {

    public static final String PINK = "#FFC0CB";
    public static final String Crimson = "#DC143C"; // 	猩红
    public static final String BLUE = "#0000FF"; // 蓝色

    public static String of(Color color) {
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();
        return "";
    }

    /**
     * 将十六进制颜色代码转化为Color
     * @param colorCode 十六进制颜色代码
     * @return Color
     */
    public static Color of(String colorCode) {
        if (colorCode.startsWith("#")) {
            colorCode = colorCode.substring(1);
        }
        int color = Integer.parseInt(colorCode, 16);
        return of(color);
    }

    /**
     * 将十六进制颜色代码转化为Color
     * @param colorCode 十进制（非十六进制）颜色代码
     * @return Color
     */
    public static Color of(int colorCode) {
        int red = (colorCode >> 16) & 0xff;
        int green = (colorCode >> 8) & 0xff;
        int blue = colorCode & 0xff;
        return new Color((double) red / 255, (double) green / 255, (double) blue / 255, 1.0);
    }

    public static void main(String[] args) {

        final int code = Integer.parseInt("59A869", 16);
        System.out.println("十进制:" + code);

        final Color color = of(code);
        System.out.println(color);
    }
}
