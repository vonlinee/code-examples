package de.saxsys.mvvmfx.core;

import javafx.scene.Parent;

public abstract class AbstractNodeView<T extends ViewModel, R extends Parent> implements NodeView<T, R> {

    private T vm;

    private R root;

    public abstract void createRoot();
}
