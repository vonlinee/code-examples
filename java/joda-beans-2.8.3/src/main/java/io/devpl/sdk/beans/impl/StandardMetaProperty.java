package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.MetaProperty;

/**
 * An abstract base meta-property.
 * @param <P> the type of the property content
 */
public abstract class StandardMetaProperty<P> implements MetaProperty<P> {

    /**
     * The name of the property.
     */
    private final String name;

    /**
     * Constructor.
     * @param propertyName the property name, not empty
     */
    protected StandardMetaProperty(String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            throw new NullPointerException("Property name must not be null or empty");
        }
        this.name = propertyName;
    }

    //-----------------------------------------------------------------------
    @Override
    public String name() {
        return name;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MetaProperty<?>) {
            MetaProperty<?> other = (MetaProperty<?>) obj;
            return name().equals(other.name()) && declaringType().equals(other.declaringType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name().hashCode() ^ declaringType().hashCode();
    }

    /**
     * Returns a string that summarises the meta-property.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return declaringType().getSimpleName() + ":" + name();
    }

}
