package io.fxtras.sdk.mvc;

import io.fxtras.sdk.utils.DisposableChangeListener;
import io.fxtras.sdk.utils.DisposableInvalidationListener;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import javafx.stage.Window;
import org.bushe.swing.event.EventBus;

/**
 * 窗口控制器
 * @param <W> 窗口的类型
 */
public abstract class WindowController<W extends Window> implements EventTarget {

    /**
     * 所在的父窗口
     */
    private Window parentWindow;

    /**
     * 所在的窗口
     */
    protected W ownerWindow;

    protected final Window getParentWindow() {
        return parentWindow;
    }

    final W getOwnerWindow() {
        return ownerWindow;
    }

    public final <T> void addDisposableListener(ObservableValue<T> observable, ChangeListener<T> listener) {
        observable.addListener(new DisposableChangeListener<>(observable, listener));
    }

    public final <T> void addDisposableListener(ObservableValue<T> observable, InvalidationListener listener) {
        observable.addListener(new DisposableInvalidationListener<>(observable, listener));
    }

    @Override
    public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail;
    }
}
