package io.maker.base.lang;

import java.lang.reflect.Constructor;

public final class Box<T> implements Cloneable {

    private T something;
    private volatile boolean isEmpty;

    /**
     * @param <T>
     * @param something
     * @param placeholder
     * @return
     * @throws UnsupportedOperationException
     */
    public static <T> Box<T> wrap(T something, T placeholder) throws UnsupportedOperationException {
        Box<T> box = new Box<>();
        if (something == null) {
            if (placeholder == null) {
                throw new UnsupportedOperationException("placeholder in Box cannot be null");
            }
            box.something = placeholder;
        }
        box.something = something;
        return box;
    }

    /**
     * @param <T>
     * @param something
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> Box<T> wrap(T something, Class<T> type) throws Exception {
        final Box<T> box = new Box<>();
        if (something == null) {
            Constructor<?>[] constructors = type.getDeclaredConstructors();
            if (constructors.length >= 1) {
                try {
                    box.something = (T) constructors[0].newInstance(new Object[]{});
                } catch (Exception e) {
                    box.something = (T) new Object();
                    box.isEmpty = true;
                    throw e;
                }
            }
        }
        box.something = something;
        box.isEmpty = false;
        return box;
    }

    /**
     * @return
     */
    public final T unwrap() {
        return something;
    }

    /**
     * @param message
     * @return
     */
    public final T unwrap(String message) {
        if (isEmpty) {
            throw new RuntimeException(message);
        }
        return something;
    }

    /**
     * @return
     */
    public final boolean isEmpty() {
        return isEmpty;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Box<T> newBox = new Box<T>();
        newBox.something = this.something;
        newBox.isEmpty = this.isEmpty;
        return newBox;
    }

    /**
     * @param other
     * @return
     */
    public final boolean equals(Box<T> other) {
        return this.something.equals(other.something);
    }
}