package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.PropertyStyle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * A meta-property that exists separate from a bean.
 * <p>
 * One use case for this is to handle renamed properties in {@code SerDeserializer}.
 * @param <P> the type of the property content
 */
public final class StandaloneMetaProperty<P> extends StandardMetaProperty<P> {

    /**
     * The meta-bean, which does not have to refer to this property.
     */
    private final MetaBean metaBean;
    /**
     * The type of the property.
     */
    private final Class<P> clazz;
    /**
     * The type of the property.
     */
    private final Type type;

    //-----------------------------------------------------------------------

    /**
     * Creates a non-generified property.
     * @param <R>          the property type
     * @param propertyName the property name, not empty
     * @param metaBean     the meta-bean, which does not have to refer to this property, not null
     * @param clazz        the type of the property, not null
     * @return the meta-property, not null
     */
    public static <R> StandaloneMetaProperty<R> of(String propertyName, MetaBean metaBean, Class<R> clazz) {
        return new StandaloneMetaProperty<>(propertyName, metaBean, clazz, clazz);
    }

    /**
     * Creates a property.
     * @param <R>          the property type
     * @param propertyName the property name, not empty
     * @param metaBean     the meta-bean, which does not have to refer to this property, not null
     * @param clazz        the type of the property, not null
     * @param type         the type of the property, not null
     * @return the meta-property, not null
     */
    public static <R> StandaloneMetaProperty<R> of(String propertyName, MetaBean metaBean, Class<R> clazz, Type type) {
        return new StandaloneMetaProperty<>(propertyName, metaBean, clazz, type);
    }

    //-----------------------------------------------------------------------

    /**
     * Creates an instance.
     * @param propertyName the property name, not empty
     * @param metaBean     the meta-bean, which does not have to refer to this property, not null
     * @param clazz        the type of the property, not null
     * @param type         the type of the property, not null
     */
    private StandaloneMetaProperty(String propertyName, MetaBean metaBean, Class<P> clazz, Type type) {
        super(propertyName);
        if (metaBean == null) {
            throw new NullPointerException("MetaBean must not be null");
        }
        if (clazz == null) {
            throw new NullPointerException("Class must not be null");
        }
        if (type == null) {
            throw new NullPointerException("Type must not be null");
        }
        this.metaBean = metaBean;
        this.clazz = clazz;
        this.type = type;
    }

    //-----------------------------------------------------------------------
    @Override
    public MetaBean metaBean() {
        return metaBean;
    }

    @Override
    public Class<?> declaringType() {
        return metaBean().beanType();
    }

    @Override
    public Class<P> propertyType() {
        return clazz;
    }

    @Override
    public Type propertyGenericType() {
        return type;
    }

    @Override
    public PropertyStyle style() {
        return PropertyStyle.READ_WRITE;
    }

    //-----------------------------------------------------------------------
    @Override
    public List<Annotation> annotations() {
        return Collections.emptyList();
    }

    //-----------------------------------------------------------------------
    @Override
    public P get(Bean bean) {
        try {
            return clazz.cast(metaBean().metaProperty(name()).get(bean));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(Bean bean, Object value) {
        metaBean().metaProperty(name()).set(bean, value);
    }

}
