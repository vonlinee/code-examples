package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.view.IconKey;
import io.devpl.toolkit.fxui.view.IconMap;
import org.girod.javafx.svgimage.SVGImage;

/**
 * 字段
 */
public class FieldItem extends JavaElementItem {

    private String name;

    public FieldItem() {
        super(IconMap.loadSVG(IconKey.JAVA_FIELD));
    }

    FieldItem(SVGImage typeIcon) {
        super(typeIcon);
    }
}
