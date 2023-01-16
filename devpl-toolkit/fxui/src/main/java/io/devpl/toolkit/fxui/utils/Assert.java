package io.devpl.toolkit.fxui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Assert<T> {

    private final T bean;

    private List<String> errerMessages;

    Assert(T bean) {
        this.bean = bean;
        this.errerMessages = new ArrayList<>();
    }

    public static <T> Assert<T> target(T target) {
        return new Assert<>(target);
    }

    public Assert<T> hasText(Function<T, String> column, String message) {
        if (!StringUtils.hasText(column.apply(bean))) {
            errerMessages.add(message);
        }
        return this;
    }

    public Assert<T> assertTrue(boolean expression, String message) {
        if (!expression) {
            errerMessages.add(message);
        }
        return this;
    }

    public <V> Assert<T> assertTrue(Function<T, V> column, Predicate<V> condition, String message) {
        if (!condition.test(column.apply(bean))) {
            errerMessages.add(message);
        }
        return this;
    }

    public String getErrorMessages() {
        return String.join(";\n", errerMessages);
    }
}
