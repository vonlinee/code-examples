package io.devpl.toolkit.fxui.view.navigation.impl;

import java.util.List;

public interface DirectoryNavigationItem<T extends NavigationItem> extends NavigationItem {

    List<T> getChildren();
}
