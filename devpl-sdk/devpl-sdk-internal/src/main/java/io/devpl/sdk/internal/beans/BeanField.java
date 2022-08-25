package io.devpl.sdk.internal.beans;

/**
 * 用于区分是Bean的Field
 * @param <B>
 * @param <V>
 */
public interface BeanField<B extends Bean, V> extends Field, FieldOperation<V> , FieldValueAccess<V> {

    String name();
}
