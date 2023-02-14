package treeview;

/**
 * The tree-facing API of the tree data model. Allows for a complex data structure that is not
 * constrained by the expectations of the TreeView or TreeItem.
 *
 * @param <T> the type related to this TreeItem, its bounds is T ~ Comparable<? super T>
 */
public interface TreeModel<T extends TreeModel<? extends T>> {

    /**
     * @return
     */
    T getValue();
}

