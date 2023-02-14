package io.devpl.fxtras.controls;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

/**
 * @see javafx.scene.control.skin.ScrollPaneSkin
 * @see javafx.scene.layout.VBox
 */
public class TaggedRegionSkin extends SkinBase<TaggedRegion> implements Skin<TaggedRegion> {

    private final BehaviorBase<TaggedRegion> behavior;

    private Label label;
    private Node contentNode;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected TaggedRegionSkin(TaggedRegion control) {
        super(control);
        behavior = new TaggedRegionBehavior(control);

        initialize();
    }

    private void initialize() {
        getChildren().clear();
        TaggedRegion skinnable = getSkinnable();
        label = new Label(skinnable.getText());


        getChildren().add(label);
        if (contentNode != null) {
            getChildren().add(contentNode);
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
    }
}
