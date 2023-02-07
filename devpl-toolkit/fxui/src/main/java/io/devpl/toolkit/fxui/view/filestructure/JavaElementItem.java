package io.devpl.toolkit.fxui.view.filestructure;

import io.devpl.toolkit.fxui.utils.ResourceLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import java.util.HashMap;
import java.util.Map;

public class JavaElementItem extends TreeItem<String> {

    private static final Map<JavaVisibility, SVGImage> visibilitySVGImageMap = new HashMap<>();

    static {
        visibilitySVGImageMap.put(JavaVisibility.PUBLIC, SVGLoader.load(ResourceLoader.load("static/icon/c_public.svg")));
        visibilitySVGImageMap.put(JavaVisibility.PRIVATE, SVGLoader.load(ResourceLoader.load("static/icon/c_private.svg")));
        visibilitySVGImageMap.put(JavaVisibility.PROTECTED, SVGLoader.load(ResourceLoader.load("static/icon/c_protected.svg")));
        visibilitySVGImageMap.put(JavaVisibility.PACKAGE_VISIABLE, SVGLoader.load(ResourceLoader.load("static/icon/c_plocal.svg")));
    }

    /**
     * 可见性
     */
    private final ObjectProperty<JavaVisibility> visibility = new SimpleObjectProperty<>();

    JavaElementItem(SVGImage typeIcon) {
        HBox graphicContainer = new HBox();
        graphicContainer.setSpacing(4.0);
        graphicContainer.setAlignment(Pos.CENTER);
        graphicContainer.getChildren().addAll(typeIcon, visibilitySVGImageMap.get(JavaVisibility.PUBLIC));
        setGraphic(graphicContainer);
        visibility.addListener(new ChangeListener<JavaVisibility>() {
            @Override
            public void changed(ObservableValue<? extends JavaVisibility> observable, JavaVisibility oldValue, JavaVisibility newValue) {

            }
        });
    }

    public JavaVisibility getVisibility() {
        return visibility.get();
    }

    public ObjectProperty<JavaVisibility> visibilityProperty() {
        return visibility;
    }

    public void setVisibility(JavaVisibility visibility) {
        this.visibility.set(visibility);
    }
}
