package de.saxsys.mvvmfx.core;

import javafx.scene.Parent;

/**
 * 视图组件
 */
public abstract class ViewComponent extends Parent {

    Parent root;
    ViewModel vm;

    public final <T extends Parent> T getRoot() {
        return (T) root;
    }

    public final <T extends ViewModel> T vm() {
        return (T) vm;
    }
}
