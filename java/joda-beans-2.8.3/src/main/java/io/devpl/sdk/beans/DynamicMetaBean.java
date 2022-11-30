package io.devpl.sdk.beans;

/**
 * A dynamic meta-bean which works with {@code DynamicBean}.
 * <p>
 * A dynamic bean can have properties added or removed at any time.
 * As such, there is a different meta-bean for each dynamic bean.
 * The meta-bean allows meta-properties to be created on demand.
 */
public interface DynamicMetaBean extends MetaBean {

    /**
     * Creates a bean builder that can be used to create an instance of this bean.
     * <p>
     * All properties added to the builder will be created and appear in the result. 
     * 
     * @return the bean builder, not null
     * @throws UnsupportedOperationException if the bean cannot be created
     */
    @Override
    BeanBuilder<? extends DynamicBean> builder();

    /**
     * Get the type of the bean represented as a {@code Class}.
     * 
     * @return the type of the bean, not null
     */
    @Override
    Class<? extends DynamicBean> beanType();

    /**
     * Gets a meta-property by name.
     * <p>
     * This will not throw an exception if the meta-property name does not exist.
     * Whether a meta-property is immediately created or not is implementation dependent.
     * 
     * @param <R>  the property type, optional, enabling auto-casting
     * @param propertyName  the property name to retrieve, not null
     * @return the meta property, not null
     */
    @Override
    <R> MetaProperty<R> metaProperty(String propertyName);

    //-----------------------------------------------------------------------
    /**
     * Defines a property for the bean.
     * <p>
     * Some implementations will automatically add properties, in which case this
     * method will have no effect.
     * 
     * @param propertyName  the property name to check, not empty, not null
     * @param propertyType  the property type, not null
     */
    void metaPropertyDefine(String propertyName, Class<?> propertyType);

    /**
     * Removes a property by name.
     * 
     * @param propertyName  the property name to remove, null ignored
     */
    void metaPropertyRemove(String propertyName);

}
