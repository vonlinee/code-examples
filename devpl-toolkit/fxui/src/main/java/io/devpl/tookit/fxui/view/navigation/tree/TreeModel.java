package io.devpl.tookit.fxui.view.navigation.tree;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import java.util.List;

/**
 * 树形嵌套的TreeView数据模型
 */
public interface TreeModel {

    default TreeItem<TreeModel> newTreeItem() {
        return new TreeItem<>(this);
    }

    void setGraphic(Node graphic);

    TreeItem<TreeModel> getTreeItem();

    /**
     * 用于界面展示的值
     *
     * @return TreeCell的文本
     */
    String getDisplayValue();

    /**
     * 修改用于界面展示的值
     */
    void setDispalyValue(String dispalyValue);

    /**
     * 是否有子项
     *
     * @return boolean
     */
    boolean hasChild();

    /**
     * 获取所有子项
     *
     * @return 所有子项列表
     */
    List<? extends TreeModel> getChildren();

    /**
     * @return 如果为NULL，表示为根项目
     */
    TreeModel getParent();

    <T extends TreeModel> void setParent(T parent);

    void setTreeItem(TreeItem<TreeModel> model);

    /**
     * 转为子类型
     *
     * @param <T> 子类型
     * @return
     */
    @SuppressWarnings("unchecked")
    default <T extends TreeModel> T getThis() {
        return (T) this;
    }

    default boolean isType(Class<? extends TreeModel> type) {
        return this.getClass() == type;
    }

    /**
     * NavigationItem和对应的TreeItem是分离的，因此需要将数据绑定到TreeItem上
     * 此过程会创建子TreeItem对象，绑定到根TreeItem上
     * 将所有子项绑定到根项目中
     *
     * @param parent 当前NavigationItem对应的根TreeItem
     */
    default void attach(TreeItem<TreeModel> parent) {
        List<? extends TreeModel> children = getChildren();
        if (children == null || children.isEmpty()) {
            return;
        }
        for (TreeModel item : children) {
            TreeItem<TreeModel> newItem = item.getTreeItem();
            parent.getChildren().add(newItem);
            item.setTreeItem(newItem);
            item.attach(newItem);
        }
    }
}
