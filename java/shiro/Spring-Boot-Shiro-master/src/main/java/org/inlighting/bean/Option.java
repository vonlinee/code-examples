package org.inlighting.bean;

public class Option<T> {

    public static final Option<?> NONE = new Option<>(null);

    private final T data;

    private Option(T data) {
        this.data = data;
    }

    public T data() {
        return data;
    }
}
