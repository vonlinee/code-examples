package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.utils.ResourceLoader;
import javafx.scene.shape.SVGPath;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

public class FieldItem extends JavaElementItem {

    private String name;

    public FieldItem() {
        SVGPath
        super(SVGLoader.load(ResourceLoader.load("static/icon/field.svg")));
    }

    FieldItem(SVGImage typeIcon) {
        super(typeIcon);
    }
}
