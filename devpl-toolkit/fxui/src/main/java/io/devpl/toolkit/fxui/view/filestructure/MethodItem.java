package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.utils.ResourceLoader;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

public class MethodItem extends JavaElementItem {

    public MethodItem() {
        super(SVGLoader.load(ResourceLoader.load("static/icon/method.svg")));
    }

    MethodItem(SVGImage typeIcon) {
        super(typeIcon);
    }
}
