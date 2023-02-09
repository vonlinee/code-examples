package custom;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.util.function.BiConsumer;

/**
 * TextArea
 */
public class TextEditor extends TextArea {

    /**
     * Creates a Text node using the given styled text.
     */
    public static <S> Node createStyledTextNode(String text, S style, BiConsumer<? super Text, S> applyStyle) {
        Text t = new Text(text);
        t.setTextOrigin(VPos.TOP);
        t.getStyleClass().add("text");
        applyStyle.accept(t, style);
        return t;
    }

    @Override
    protected void layoutChildren() {
        Insets ins = getInsets();
        System.out.println(ins);
    }

    private static Bounds extendLeft(Bounds b, double w) {
        if (w == 0) {
            return b;
        } else {
            return new BoundingBox(b.getMinX() - w, b.getMinY(), b.getWidth() + w, b.getHeight());
        }
    }
}
