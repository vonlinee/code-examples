package io.devpl.toolkit.fxui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Assert<T> {

    private final T bean;

    private final List<String> errerMessages = new ArrayList<>();

    Assert(T bean) {
        this.bean = bean;
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

    public String getErrorMessages() {
        return String.join(";\n", errerMessages);
    }
}
