package io.devpl.sdk.internal.beans;

/**
 * A dynamic bean that allows properties to be added and removed.
 * <p>
 * A JavaBean is defined at compile-time and cannot have additional properties added.
 * Instances of this interface allow additional properties to be added and removed
 * probably by wrapping a map
 */
public interface DynamicBean extends Bean {

    /**
     * Gets a property by name.
     * <p>
     * This will not throw an exception if the property name does not exist.
     * Whether a property is immediately created or not is implementation dependent.
     * @param <R>       the property type, optional, enabling auto-casting
     * @param fieldName the field name to retrieve, not null
     * @return the property, not null
     */
    <R> Field<R> getField(String fieldName);

    /**
     * Adds a property to those allowed to be stored in the bean.
     * <p>
     * Some implementations will automatically add properties, in which case this
     * method will have no effect.
     * @param fieldName the property name to check, not empty, not null
     * @param type      the property type, not null
     */
    void define(String fieldName, Class<?> type);

    /**
     * Removes a property by name.
     * @param fieldName the property name to remove, null ignored
     */
    void removeField(String fieldName);
}
