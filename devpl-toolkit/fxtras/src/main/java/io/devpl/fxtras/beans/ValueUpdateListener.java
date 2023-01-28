package io.devpl.fxtras.beans;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.function.BiConsumer;

public class ValueUpdateListener<B, T> implements ChangeListener<T> {

    private final B bean;
    private final BiConsumer<B, T> setter;

    public ValueUpdateListener(B bean, BiConsumer<B, T> setter) {
        this.bean = bean;
        this.setter = setter;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        setter.accept(bean, newValue);
    }
}
