package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;

import java.util.List;

/**
 * 目录导航项
 * 
 * @date 2023年1月31日 09:27:23
 * @param <T>
 */
public interface DirectoryNavigationItem<T extends TreeModel> extends TreeModel {

    List<T> getChildren();
}
