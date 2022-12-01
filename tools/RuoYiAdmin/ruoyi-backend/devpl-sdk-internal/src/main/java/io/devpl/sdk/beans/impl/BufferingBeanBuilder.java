package io.devpl.sdk.beans.impl;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementation of {@code BeanBuilder} that buffers data in a local map.
 * <p>
 * This is useful for cases where the builder data might be manipulated before
 * the final build. The buffer can be directly mutated.
 * @param <T> the bean type
 */
public class BufferingBeanBuilder<T extends Bean> implements BeanBuilder<T> {

    /**
     * The target meta-bean.
     */
    private final MetaBean metaBean;
    /**
     * The buffered data.
     */
    private final ConcurrentMap<MetaProperty<?>, Object> buffer = new ConcurrentHashMap<>();

    //-----------------------------------------------------------------------

    /**
     * Constructs the builder wrapping the target bean.
     * @param metaBean the target meta-bean, not null
     * @return a new untyped builder, not null
     */
    public static BufferingBeanBuilder<?> of(MetaBean metaBean) {
        return new BufferingBeanBuilder<>(metaBean);
    }

    //-----------------------------------------------------------------------

    /**
     * Constructs the builder wrapping the target bean.
     * @param metaBean the target meta-bean, not null
     */
    public BufferingBeanBuilder(MetaBean metaBean) {
        if (metaBean == null) {
            throw new NullPointerException("MetaBean must not be null");
        }
        this.metaBean = metaBean;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the meta-beans.
     * @return the meta-bean, not null
     */
    public MetaBean getMetaBean() {
        return metaBean;
    }

    /**
     * Gets the buffer holding the state of the builder.
     * <p>
     * The buffer may be mutated.
     * @return the mutable buffer, not null
     */
    public ConcurrentMap<MetaProperty<?>, Object> getBuffer() {
        return buffer;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the buffered value associated with the specified property name.
     * @param propertyName the property name, not null
     * @return the current value in the builder, null if not found or value is null
     */
    @Override
    public Object get(String propertyName) {
        return get(getMetaBean().metaProperty(propertyName));
    }

    /**
     * Gets the buffered value associated with the specified property name.
     * @param metaProperty the meta-property, not null
     * @return the current value in the builder, null if not found or value is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        return (P) getBuffer().get(metaProperty);
    }

    //-----------------------------------------------------------------------
    @Override
    public BeanBuilder<T> set(String propertyName, Object value) {
        return set(getMetaBean().metaProperty(propertyName), value);
    }

    @Override
    public BeanBuilder<T> set(MetaProperty<?> metaProperty, Object value) {
        if (value != null) {
            // ConcurrentHashMap does not allow null values and setting to null is equivalent to not setting
            getBuffer().put(metaProperty, value);
        }
        return this;
    }

    @Override
    public T build() {
        @SuppressWarnings("unchecked")
        BeanBuilder<T> builder = (BeanBuilder<T>) getMetaBean().builder();
        for (Entry<MetaProperty<?>, Object> entry : getBuffer().entrySet()) {
            builder.set(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a string that summarises the builder.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return "BeanBuilder for " + metaBean.beanName();
    }

}
