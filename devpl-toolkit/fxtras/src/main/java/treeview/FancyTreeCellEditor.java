package treeview;

import javafx.scene.Node;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface FancyTreeCellEditor {
    Node getNode();

    void setCell(FancyTreeCell cell);

    void cancelEdit();
}

