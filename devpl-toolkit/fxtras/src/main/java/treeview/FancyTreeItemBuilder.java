package treeview;

import javafx.scene.control.TreeItem;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
class FancyTreeItemBuilder {
    static TreeItem<TreeModel> create(TreeModel root) {
        TreeItem<TreeModel> root_item = new TreeItem<>(root);
        root.setTreeItemFacade(new FancyTreeItemFacade(root_item));

        addChildren(root_item, root);
        return root_item;
    }

    private static void addChildren(TreeItem item, TreeModel node) {
        for (Object child_node : node.getChildren())
            addChild(item, (TreeModel) child_node);
    }

    private static void addChild(TreeItem item, TreeModel child_node) {
        addChild(item, child_node, item.getChildren().size());
    }

    static void addChild(TreeItem item, TreeModel child_node, int index) {
        TreeItem child_item = new TreeItem<>(child_node);
        item.getChildren().add(index, child_item);
        addChildren(child_item, child_node);

        child_node.setTreeItemFacade(new FancyTreeItemFacade(child_item));
    }
}


