package resources;

import javafx.scene.Node;
import org.girod.javafx.svgimage.SVGLoader;

import java.net.URL;

public class Icon {

    public static Node of(String sourceRoot) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(sourceRoot);
        return SVGLoader.load(resource);
    }
}
