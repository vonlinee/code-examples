package io.fxtras.sdk.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;

import java.util.Objects;

/**
 * Disposable ChangeListener
 * @param <T>
 */
public class DisposableInvalidationListener<T> implements InvalidationListener {

    private InvalidationListener listener;
    private ObservableValue<T> observable;

    public DisposableInvalidationListener(ObservableValue<T> observable, InvalidationListener listener) {
        this.listener = Objects.requireNonNull(listener);
        this.observable = Objects.requireNonNull(observable);
        this.observable.addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        listener.invalidated(observable);
        dispose();
    }

    public void dispose() {
        this.observable.removeListener(this);
        this.listener = null;
        this.observable = null;
    }
}
