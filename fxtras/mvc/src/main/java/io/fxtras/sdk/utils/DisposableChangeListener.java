package io.fxtras.sdk.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Objects;

/**
 * Disposable ChangeListener
 * @param <T>
 */
public class DisposableChangeListener<T> implements ChangeListener<T> {

    private ChangeListener<T> listener;
    private ObservableValue<T> observable;

    public DisposableChangeListener(ObservableValue<T> observable, ChangeListener<T> listener) {
        this.listener = Objects.requireNonNull(listener);
        this.observable = Objects.requireNonNull(observable);
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        listener.changed(observable, oldValue, newValue);
        dispose();
    }

    public void dispose() {
        this.observable.removeListener(this);
        this.listener = null;
        this.observable = null;
    }
}
