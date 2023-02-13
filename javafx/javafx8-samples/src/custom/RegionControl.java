package custom;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

import java.util.function.Consumer;

public class RegionControl extends Region {
    public enum Type {CLOSE, MINIMIZE, ZOOM}

    private static final double PREFERRED_WIDTH = 12;
    private static final double PREFERRED_HEIGHT = 12;
    private static final double MINIMUM_WIDTH = 12;
    private static final double MINIMUM_HEIGHT = 12;
    private static final double MAXIMUM_WIDTH = 12;
    private static final double MAXIMUM_HEIGHT = 12;
    private static final PseudoClass CLOSE_PSEUDO_CLASS = PseudoClass.getPseudoClass("close");
    private static final PseudoClass MINIMIZE_PSEUDO_CLASS = PseudoClass.getPseudoClass("minimize");
    private static final PseudoClass ZOOM_PSEUDO_CLASS = PseudoClass.getPseudoClass("zoom");
    private static final PseudoClass HOVERED_PSEUDO_CLASS = PseudoClass.getPseudoClass("hovered");
    private static final PseudoClass PRESSED_PSEUDO_CLASS = PseudoClass.getPseudoClass("pressed");
    private BooleanProperty hovered;
    private static String userAgentStyleSheet;
    private ObjectProperty<Type> type;
    private double size;
    private double width;
    private double height;
    private Circle circle;
    private Region symbol;
    private Consumer<MouseEvent> mousePressedConsumer;
    private Consumer<MouseEvent> mouseReleasedConsumer;
}