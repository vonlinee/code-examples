package treeview;

import javafx.scene.control.TreeView;
import javafx.scene.control.skin.TreeViewSkin;
import javafx.scene.control.skin.VirtualFlow;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
class FancyTreeViewSkin extends TreeViewSkin {
    FancyTreeViewSkin(TreeView tree) {
        super(tree);
    }

    boolean isIndexVisible(int index) {
        VirtualFlow flow = getVirtualFlow();
        if (flow.getFirstVisibleCell() != null &&
                flow.getLastVisibleCell() != null &&
                flow.getFirstVisibleCell().getIndex() <= index &&
                flow.getLastVisibleCell().getIndex() >= index)
            return true;
        return false;
    }
}


