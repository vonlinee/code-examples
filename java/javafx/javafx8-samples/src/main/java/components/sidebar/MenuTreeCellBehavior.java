package components.sidebar;

import com.sun.javafx.scene.control.behavior.CellBehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.util.List;

public class MenuTreeCellBehavior extends CellBehaviorBase<MenuTreeCell> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     *************************************************************************
     * @param control
     * @param bindings*/
    public MenuTreeCellBehavior(MenuTreeCell control, List<KeyBinding> bindings) {
        super(control, bindings);
    }

    @Override
    protected MultipleSelectionModel<TreeItem<String>> getSelectionModel() {
        return getCellContainer().getSelectionModel();
    }

    @Override
    protected FocusModel<TreeItem<String>> getFocusModel() {
        return getCellContainer().getFocusModel();
    }

    @Override
    protected TreeView<String> getCellContainer() {
        return getControl().getTreeView();
    }

    @Override
    protected void edit(MenuTreeCell cell) {
        TreeItem<String> treeItem = cell == null ? null : cell.getTreeItem();
        getCellContainer().edit(treeItem);
    }

    @Override
    protected void handleClicks(MouseButton button, int clickCount, boolean isAlreadySelected) {
        // handle editing, which only occurs with the primary mouse button
        TreeItem<String> treeItem = getControl().getTreeItem();
        if (button == MouseButton.PRIMARY) {
            if (clickCount == 1 && isAlreadySelected) {
                edit(getControl());
            } else if (clickCount == 1) {
                // cancel editing
                edit(null);
            } else if (clickCount == 2 && treeItem.isLeaf()) {
                // attempt to edit
                edit(getControl());
            } else if (clickCount % 2 == 0) {
                // try to expand/collapse branch tree item
                treeItem.setExpanded(!treeItem.isExpanded());
            }
        }
    }

    @Override
    protected boolean handleDisclosureNode(double x, double y) {
        TreeCell<String> treeCell = getControl();
        Node disclosureNode = treeCell.getDisclosureNode();
        if (disclosureNode != null) {
            if (disclosureNode.getBoundsInParent().contains(x, y)) {
                if (treeCell.getTreeItem() != null) {
                    treeCell.getTreeItem().setExpanded(!treeCell.getTreeItem().isExpanded());
                }
                return true;
            }
        }
        return false;
    }
}
