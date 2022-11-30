package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;

import java.util.Objects;

/**
 * A property that binds a {@code Bean} to a {@code MetaProperty}.
 * <p>
 * This is the standard implementation of a property.
 * It defers the strategy of getting and setting the value to the meta-property.
 * <p>
 * This implementation is also a map entry to aid performance in maps.
 * @param <P> the type of the property content
 */
public class StandardProperty<P> implements Property<P> {

    /**
     * The bean that the property is bound to.
     */
    private final Bean bean;

    /**
     * The meta-property that the property is bound to.
     */
    private final MetaProperty<P> metaProperty;

    /**
     * Factory to create a property avoiding duplicate generics.
     * @param <P>          the property type
     * @param bean         the bean that the property is bound to, not null
     * @param metaProperty the meta property, not null
     * @return the property, not null
     */
    public static <P> StandardProperty<P> of(Bean bean, MetaProperty<P> metaProperty) {
        return new StandardProperty<>(bean, metaProperty);
    }

    /**
     * Creates a property binding the bean to the meta-property.
     * @param bean         the bean that the property is bound to, not null
     * @param metaProperty the meta property, not null
     */
    private StandardProperty(Bean bean, MetaProperty<P> metaProperty) {
        if (bean == null) {
            throw new NullPointerException("Bean must not be null");
        }
        if (metaProperty == null) {
            throw new NullPointerException("MetaProperty must not be null");
        }
        this.bean = bean;
        this.metaProperty = metaProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends Bean> B bean() {
        return (B) bean;
    }

    @Override
    public MetaProperty<P> metaProperty() {
        return metaProperty;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Property) {
            Property<?> other = (Property<?>) obj;
            if (metaProperty.equals(other.metaProperty())) {
                Object a = get();
                Object b = other.get();
                return Objects.equals(a, b);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        P value = get();
        return metaProperty.hashCode() ^ (value == null ? 0 : value.hashCode());
    }

    /**
     * Returns a string that summarises the property.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return metaProperty + "=" + get();
    }

}
