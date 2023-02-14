package treeview;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.skin.TreeViewSkin;
import javafx.scene.control.skin.VirtualFlow;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class RecursiveTreeViewSkin extends TreeViewSkin<RecursiveTreeView> {

    RecursiveTreeViewSkin(TreeView tree) {
        super(tree);
    }

    boolean isIndexVisible(int index) {
        VirtualFlow<TreeCell<RecursiveTreeView>> flow = getVirtualFlow();
        return flow.getFirstVisibleCell() != null && flow.getLastVisibleCell() != null && flow.getFirstVisibleCell()
                .getIndex() <= index && flow.getLastVisibleCell().getIndex() >= index;
    }
}


