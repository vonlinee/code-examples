package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.view.IconKey;
import io.devpl.toolkit.fxui.view.IconMap;
import org.girod.javafx.svgimage.SVGImage;

public class MethodItem extends JavaElementItem {

    public MethodItem() {
        super(IconMap.loadSVG(IconKey.JAVA_METHOD));
    }

    MethodItem(SVGImage typeIcon) {
        super(typeIcon);
    }
}
