package io.devpl.sdk.beans;

/**
 * 访问Bean的属性
 */
public interface DynamicBeanAccess extends BeanAccess {

    <V> boolean defineField(String name, Class<V> type);

    <V> boolean addField(String name, V value, Class<V> type);

    boolean removeField(String name);

    boolean containsField(String name);
}
