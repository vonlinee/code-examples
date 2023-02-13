package io.devpl.toolkit.fxui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validator<T> {

    private final T bean;

    private final List<String> errerMessages;

    Validator(T bean) {
        this.bean = bean;
        this.errerMessages = new ArrayList<>();
    }

    public static <T> Validator<T> target(T target) {
        return new Validator<>(target);
    }

    public Validator<T> hasText(Function<T, String> column, String message) {
        if (!StringUtils.hasText(column.apply(bean))) {
            errerMessages.add(message);
        }
        return this;
    }

    public Validator<T> assertTrue(boolean expression, String message) {
        if (!expression) {
            errerMessages.add(message);
        }
        return this;
    }

    public <V> Validator<T> assertTrue(Function<T, V> column, Predicate<V> condition, String message) {
        if (!condition.test(column.apply(bean))) {
            errerMessages.add(message);
        }
        return this;
    }

    public String getErrorMessages() {
        return String.join(";\n", errerMessages);
    }
}