package io.devpl.fxtras.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * @see javafx.scene.control.ScrollPane
 */
public class TaggedRegion extends Control {

    public TaggedRegion(String text) {
        textProperty().set(text);
    }

    private StringProperty text;

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty(this, "text");
        }
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    /**
     * The node used as the content of this TaggedRegion.
     */
    private ObjectProperty<Node> content;

    public final void setContent(Node value) {
        contentProperty().set(value);
    }

    public final Node getContent() {
        return content == null ? null : content.get();
    }

    public final ObjectProperty<Node> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<Node>(this, "content");
        }
        return content;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TaggedRegionSkin(this);
    }
}
