package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.utils.ResourceLoader;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

public class ClassItem extends JavaElementItem {

    public ClassItem() {
        super(SVGLoader.load(ResourceLoader.load("static/icon/class.svg")));
    }

    ClassItem(SVGImage typeIcon) {
        super(typeIcon);
    }

    public void addMethod(MethodItem methodItem) {
        getChildren().add(methodItem);
    }

    public void addField(FieldItem fieldItem) {
        getChildren().add(fieldItem);
    }
}
