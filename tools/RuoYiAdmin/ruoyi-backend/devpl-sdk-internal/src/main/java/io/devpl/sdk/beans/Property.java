package io.devpl.sdk.beans;

/**
 * A property that is linked to a specific bean.
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 * @param <P> the type of the property content
 */
public interface Property<P> {

    /**
     * Gets the bean which owns this property.
     * <p>
     * Each property is fully owned by a single bean.
     * @param <B> the bean type
     * @return the bean, not null
     */
    <B extends Bean> B bean();

    /**
     * Gets the meta-property representing the parts of the property that are
     * common across all instances, such as the name.
     * @return the meta-property, not null
     */
    MetaProperty<P> metaProperty();

    /**
     * Gets the property name.
     * <p>
     * The JavaBean style methods getFoo() and setFoo() will lead to a property
     * name of 'foo' and so on.
     * @return the name of the property, not empty
     */
    default String name() {
        return metaProperty().name();
    }

    /**
     * Gets the value of the property for the associated bean.
     * <p>
     * For a JavaBean, this is the equivalent to calling <code>getFoo()</code> on the bean itself.
     * Alternate implementations may perform any logic to obtain the value.
     * @return the value of the property on the bound bean, may be null
     * @throws UnsupportedOperationException if the property is write-only
     */
    default P get() {
        try {
            return metaProperty().get(bean());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the value of the property on the associated bean.
     * <p>
     * The value must be of the correct type for the property.
     * See the meta-property for string conversion.
     * For a standard JavaBean, this is equivalent to calling <code>setFoo()</code> on the bean.
     * Alternate implementations may perform any logic to change the value.
     * @param value the value to set into the property on the bean
     * @throws ClassCastException            if the value is of an invalid type for the property
     * @throws UnsupportedOperationException if the property is read-only
     * @throws RuntimeException              if the value is rejected by the property (use appropriate subclasses)
     */
    default void set(Object value) {
        metaProperty().set(bean(), value);
    }

    /**
     * Sets the value of the property on the associated bean and returns the previous value.
     * <p>
     * This is a combination of the {@code get} and {@code set} methods that matches the definition
     * of {@code put} in a {@code Map}.
     * @param value the value to set into the property on the bean
     * @return the old value of the property, may be null
     * @throws ClassCastException            if the value is of an invalid type for the property
     * @throws UnsupportedOperationException if the property is read-only
     * @throws RuntimeException              if the value is rejected by the property (use appropriate subclasses)
     */
    default P put(Object value) {
        return metaProperty().put(bean(), value);
    }

    //-----------------------------------------------------------------------

    /**
     * Checks if this property equals another.
     * <p>
     * This compares the meta-property and value.
     * It does not consider the property or bean types.
     * @param obj the other property, null returns false
     * @return true if equal
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a suitable hash code.
     * @return the hash code
     */
    @Override
    int hashCode();
}
