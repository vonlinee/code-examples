package io.devpl.fxtras.beans;

public interface BeanHelper {

    default void bind() {
        final Class<? extends BeanHelper> aClass = this.getClass();
    }
}
