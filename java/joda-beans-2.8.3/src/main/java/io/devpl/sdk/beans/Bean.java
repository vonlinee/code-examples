package io.devpl.sdk.beans;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A bean consisting of a set of properties.
 * <p>
 * The implementation may be any class, but is typically a standard JavaBean
 * with get/set methods. Alternate implementations might store the properties
 * in another data structure such as a map.
 */
public interface Bean extends Serializable {

    /**
     * 该Bean的名称，作用和实体类的Class.getName相同
     * @return Bean的名称
     */
    default String getName() {
        return this.getClass().getName();
    }

    /**
     * Gets the meta-bean representing the parts of the bean that are
     * common across all instances, such as the set of meta-properties.
     * <p>
     * The meta-bean can be thought of as the equivalent of {@link Class} but for beans.
     * @return the meta-bean, not null
     */
    MetaBean metaBean();

    /**
     * Gets a property by name.
     * <p>
     * Each bean consists of a known set of properties.
     * This method checks whether there is a property with the specified name.
     * <p>
     * The base interface throws an exception if the name is not recognised.
     * By contrast, the {@code DynamicBean} subinterface creates the property on demand.
     * @param <R>          the property type, optional, enabling auto-casting
     * @param propertyName the property name to retrieve, not null
     * @return the property, not null
     * @throws NoSuchElementException if the property name is invalid
     */
    default <R> Property<R> getProperty(String propertyName) {
        return metaBean().<R>metaProperty(propertyName).createProperty(this);
    }

    /**
     * Gets the set of property names.
     * <p>
     * Each bean consists of a known set of properties.
     * This method returns the known property names.
     * @return the unmodifiable set of property names, not null
     */
    default Set<String> propertyNames() {
        return metaBean().metaPropertyMap().keySet();
    }
}
