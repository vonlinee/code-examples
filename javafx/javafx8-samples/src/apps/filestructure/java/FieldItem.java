package apps.filestructure.java;

import javafx.scene.control.TreeItem;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import utils.ResourceLoader;

public class FieldItem extends TreeItem<String> {

    private String name;

    public void attach(TreeItem<String> parent) {
        parent.getChildren().add(this);
        SVGImage svgImage = SVGLoader.load(ResourceLoader.load(getClass(), "icons/field.svg"));
        this.setGraphic(svgImage);
    }
}
