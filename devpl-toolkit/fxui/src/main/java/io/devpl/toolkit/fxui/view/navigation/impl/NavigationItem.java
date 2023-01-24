package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.scene.control.TreeItem;

import java.util.List;

/**
 * 导航TreeView数据模型
 */
public interface NavigationItem {

    /**
     * 用于界面展示的值
     * @return TreeCell的文本
     */
    String getDispalyValue();

    /**
     * 修改用于界面展示的值
     */
    void setDispalyValue(String dispalyValue);

    /**
     * 是否有子项
     * @return boolean
     */
    boolean hasChild();

    /**
     * 获取所有子项
     * @return 所有子项列表
     */
    List<? extends NavigationItem> getChildren();

    /**
     * 将所有子项绑定到根项目中
     * @param parent 根项目
     */
    default void attach(TreeItem<NavigationItem> parent) {
        List<? extends NavigationItem> children = getChildren();
        if (children == null || children.isEmpty()) {
            return;
        }
        for (NavigationItem item : children) {
            TreeItem<NavigationItem> newItem = new TreeItem<>(item);
            parent.getChildren()
                    .add(newItem);
            item.attach(newItem);
        }
    }
}
