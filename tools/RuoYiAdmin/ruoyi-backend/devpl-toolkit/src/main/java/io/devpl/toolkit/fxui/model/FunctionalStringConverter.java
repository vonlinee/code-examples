package io.devpl.toolkit.fxui.model;

import javafx.util.StringConverter;

import java.util.function.Function;

public class FunctionalStringConverter<T> extends StringConverter<T> {

    private final Function<T, String> toStringSupplier;
    private final Function<String, T> fromStringSupplier;

    public FunctionalStringConverter(Function<T, String> toStringSupplier, Function<String, T> fromStringSupplier) {
        this.toStringSupplier = toStringSupplier;
        this.fromStringSupplier = fromStringSupplier;
    }

    @Override
    public String toString(T object) {
        return toStringSupplier.apply(object);
    }

    @Override
    public T fromString(String string) {
        return fromStringSupplier.apply(string);
    }
}
