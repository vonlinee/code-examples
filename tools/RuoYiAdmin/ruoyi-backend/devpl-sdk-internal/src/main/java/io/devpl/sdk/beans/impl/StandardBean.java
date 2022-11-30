package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.JodaBeanUtils;

/**
 * Basic implementation of {@code Bean} intended for applications to subclass.
 * <p>
 * The subclass must to provide an implementation for {@link Bean#metaBean()}.
 * This returns the complete definition of the bean at the meta level.
 */
public abstract class StandardBean implements Bean {

    /**
     * Clones this bean, returning an independent copy.
     * @return the clone, not null
     */
    @Override
    public StandardBean clone() {
        return JodaBeanUtils.clone(this);
    }

    /**
     * Checks if this bean equals another.
     * <p>
     * This compares the class and all the properties of the bean.
     * @param obj the object to compare to, null returns false
     * @return true if the beans are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Bean other = (Bean) obj;
            return JodaBeanUtils.propertiesEqual(this, other);
        }
        return false;
    }

    /**
     * Returns a suitable hash code.
     * <p>
     * The hash code is derived from all the properties of the bean.
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return getClass().hashCode() ^ JodaBeanUtils.propertiesHashCode(this);
    }

    /**
     * Returns a string that summarises the bean.
     * <p>
     * The string contains the class name and properties.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return JodaBeanUtils.propertiesToString(this, metaBean().beanType().getSimpleName());
    }

}
