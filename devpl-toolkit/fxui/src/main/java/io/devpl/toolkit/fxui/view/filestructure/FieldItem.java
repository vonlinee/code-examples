package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.view.IconKey;
import io.devpl.toolkit.fxui.view.IconMap;

/**
 * 字段
 */
public class FieldItem extends JavaElementItem {

    private String name;

    public FieldItem() {
        super(IconMap.loadSVG(IconKey.JAVA_FIELD));
    }
}
