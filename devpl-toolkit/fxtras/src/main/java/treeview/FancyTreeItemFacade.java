package treeview;

import javafx.scene.control.TreeItem;

/**
 * The model-facing API to a tree item. Allows the model to notify the tree of asynchronous changes
 * that require updates in the tree.
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class FancyTreeItemFacade {
    @SuppressWarnings("WeakerAccess")  // part of the public API
    public FancyTreeItemFacade(TreeItem<TreeModel> item) {
        _item = item;
    }

    /**
     * Re-render the node. Should be called when non-structural changes require a change to the visual presentation.
     */
    public void refreshDisplay() {
        _item.setValue(_item.getValue().copyAndDestroy());
    }

    public void addChild(TreeModel child, int index) {
        FancyTreeItemBuilder.addChild(_item, child, index);
    }

    public void removeChild(int index, TreeModel child) {
        try {
            TreeModel node = _item.getChildren().get(index).getValue();
            if (child == null || node.getModelNode().equals(child.getModelNode())) {
                TreeItem<TreeModel> remove_item = _item.getChildren().remove(index);
                remove_item.getValue().destroy();
            } else
                throw new IllegalArgumentException(String.format("The indexed sub-item (%d) didn't match the node selected for removal: %s", index, child.getModelNode()
                        .toString()));
        } catch (Exception e) {
            // index doesn't exist
            String child_description = "(unknown)";
            if (child != null)
                child_description = child.getModelNode().toString();
            throw new IllegalArgumentException(String.format("Unable to locate the indexed sub-item (%d) for removal: %s", index, child_description));
        }
    }

    private final TreeItem<TreeModel> _item;
}

