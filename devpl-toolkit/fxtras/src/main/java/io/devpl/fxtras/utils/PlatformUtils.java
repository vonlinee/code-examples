package io.devpl.fxtras.utils;

import com.sun.javafx.PlatformUtil;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * 与平台相关的工具类
 */
public final class PlatformUtils {

    public static final double SCREEN_WIDTH;
    public static final double SCREEN_HEIGHT;

    static {
        final Rectangle2D bounds = primaryScreenBounds();
        SCREEN_WIDTH = bounds.getWidth();
        SCREEN_HEIGHT = bounds.getHeight();
    }

    public static Rectangle2D primaryScreenBounds() {
        // 获取主屏幕
        // This operation is permitted on the event thread only; currentThread = main
        final Screen primaryScreen = Screen.getScreens().get(0);
        return primaryScreen.getBounds();
    }

    public static boolean isWindows() {
        return PlatformUtil.isWindows();
    }
}
