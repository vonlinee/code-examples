package io.devpl.sdk.beans.impl.direct;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaProperty;

/**
 * A builder implementation designed for use by the code generator.
 * <p>
 * This implementation is intended to have fields generated in the subclass.
 * @param <T> the bean type
 */
public abstract class DirectPrivateBeanBuilder<T extends Bean> implements BeanBuilder<T> {

    /**
     * Constructs the builder.
     */
    protected DirectPrivateBeanBuilder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        return (P) get(metaProperty.name());
    }

    //-----------------------------------------------------------------------
    @Override
    public BeanBuilder<T> set(MetaProperty<?> metaProperty, Object value) {
        try {
            set(metaProperty.name(), value);
            return this;
        } catch (RuntimeException ex) {
            if (value == "") {
                return this;
            }
            throw ex;
        }
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a string that summarises the builder.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return "BeanBuilder";
    }

}
