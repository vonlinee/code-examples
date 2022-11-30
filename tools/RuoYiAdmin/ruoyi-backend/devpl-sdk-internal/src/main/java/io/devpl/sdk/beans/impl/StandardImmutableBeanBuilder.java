package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;

/**
 * Basic implementation of {@code BeanBuilder} that wraps a {@code MetaBean}.
 * <p>
 * The subclass implementation generally has concrete fields for each property.
 * This class has effectively been replaced by {@link DirectFieldsBeanBuilder}.
 * It is retained for situations where the builder is being implemented manually.
 * @param <T> the bean type
 */
public abstract class StandardImmutableBeanBuilder<T extends Bean> implements BeanBuilder<T> {

    /**
     * The meta bean.
     */
    private final MetaBean meta;

    /**
     * Constructs the builder.
     * @param meta the meta-bean, not null
     */
    public StandardImmutableBeanBuilder(MetaBean meta) {
        this.meta = meta;
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        return get(meta.metaProperty(propertyName));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        return (P) get(metaProperty.name());
    }

    //-----------------------------------------------------------------------
    @Override
    public BeanBuilder<T> set(MetaProperty<?> metaProperty, Object value) {
        set(metaProperty.name(), value);
        return this;
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
