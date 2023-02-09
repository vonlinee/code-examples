package io.devpl.toolkit.fxui.view;

import io.devpl.fxtras.utils.WeakValueHashMap;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import java.net.URL;
import java.util.Map;

/**
 * 图标映射关系：决定哪些节点展示哪种图标
 * Ikonli提供的图标
 * 本地SVG图标
 */
public class IconMap {

    /**
     * 图标映射关系：key-图标所在路径
     */
    private static final Map<String, SVGImage> svgIconMappings = new WeakValueHashMap<>();

    public static SVGImage loadSVG(String key) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(key);
        return SVGLoader.load(resource);
    }
}
