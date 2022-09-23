package io.devpl.sdk.internal.beans;

public interface BeanOperation {

    /**
     * return the name of this bean
     * @return the name of this bean
     */
    String name();

    /**
     * Gets a property by name.
     * <p>
     * This will not throw an exception if the property name does not exist.
     * Whether a property is immediately created or not is implementation dependent.
     * @param fieldName the field name to retrieve, not null
     * @return the property, not null
     */
    <V, T extends Field<V>> T lookup(String fieldName);

    /**
     * Adds a property to those allowed to be stored in the bean.
     * <p>
     * Some implementations will automatically add properties, in which case this
     * method will have no effect.
     * @param fieldName the property name to check, not empty, not null
     * @param type      the property type, not null
     */
    <V> void define(String fieldName, Class<V> type);

    /**
     * Removes a property by name.
     * @param fieldName the property name to remove, null ignored
     */
    void remove(String fieldName);
}
