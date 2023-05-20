package com.panemu.tiwulfx.utils;

import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图标工具类
 * 根据图标代码来转换图标
 */
public class Icon {

    public static Map<String, Ikon> iconMap = new LinkedHashMap<>();

    /**
     * 所有图标均使用ikonli库提供的
     * @param iconCode Ikon枚举
     * @return 图标节点
     */
    public static Node of(Ikon iconCode) {
        return FontIcon.of(iconCode);
    }
}
