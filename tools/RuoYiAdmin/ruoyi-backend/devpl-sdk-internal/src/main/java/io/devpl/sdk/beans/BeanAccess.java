package io.devpl.sdk.beans;

/**
 * @see Bean
 */
public interface BeanAccess {

    String id();

    <V> Field<V> getField(String name);

    <V> V get(String name);

    <V> V set(String name, V value);

    int fieldCount();

    String[] fieldNames();

    MetaBean meta();
}
