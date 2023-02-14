package io.devpl.fxtras.controls;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.inputmap.InputMap;

/**
 * @see com.sun.javafx.scene.control.behavior.ScrollPaneBehavior
 */
public class TaggedRegionBehavior extends BehaviorBase<TaggedRegion> {

    private final InputMap<TaggedRegion> inputMap;

    public TaggedRegionBehavior(TaggedRegion node) {
        super(node);

        // create a map for scrollpane-specific mappings (this reuses the default
        // InputMap installed on the control, if it is non-null, allowing us to pick up any user-specified mappings)
        inputMap = createInputMap();
    }

    @Override
    public InputMap<TaggedRegion> getInputMap() {
        return null;
    }

}
