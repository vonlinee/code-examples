package io.devpl.sdk.beans;

/**
 * A dynamic bean that allows properties to be added and removed.
 * <p>
 * A JavaBean is defined at compile-time and cannot have additional properties added.
 * Instances of this interface allow additional properties to be added and removed
 * probably by wrapping a map
 */
public interface DynamicBean extends Bean {

    /**
     * Gets the meta-bean representing the parts of the bean that are
     * common across all instances, such as the set of meta-properties.
     * @return the meta-bean, not null
     */
    @Override
    DynamicMetaBean metaBean();

    /**
     * 指定名称的属性是否存在
     * @param name 属性名称, not null
     * @return 指定名称的属性是否存在
     */
    boolean isPropertyExisted(String name);

    /**
     * Gets a property by name.
     * <p>
     * This will not throw an exception if the property name does not exist.
     * Whether a property is immediately created or not is implementation dependent.
     * @param <R>  the property type, optional, enabling auto-casting
     * @param name the property name to retrieve, not null
     * @return the property, not null
     */
    @Override
    <R> Property<R> getProperty(String name);

    /**
     * Adds a property to those allowed to be stored in the bean.
     * <p>
     * Some implementations will automatically add properties, in which case this
     * method will have no effect.
     * @param name the property name to check, not empty, not null
     * @param type the property type, not null
     */
    void defineProperty(String name, Class<?> type);

    /**
     * Removes a property by name.
     * @param name the property name to remove, null ignored
     */
    void removeProperty(String name);
}
