package io.devpl.sdk.beans.impl;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;

/**
 * A standard map of properties.
 * <p>
 * This is the standard implementation of a map of properties derived from a meta-bean.
 */
public final class StandardPropertyMap extends AbstractMap<String, Property<?>> {

    /**
     * The bean.
     */
    private final Bean bean;

    /**
     * Factory to create a property map avoiding duplicate generics.
     * @param bean the bean
     * @return the property map, not null
     */
    public static StandardPropertyMap of(Bean bean) {
        return new StandardPropertyMap(bean);
    }

    /**
     * Creates a property map.
     * @param bean the bean that the property is bound to, not null
     */
    private StandardPropertyMap(Bean bean) {
        if (bean == null) {
            throw new NullPointerException("Bean must not be null");
        }
        this.bean = bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public int size() {
        return bean.metaBean().metaPropertyCount();
    }

    @Override
    public boolean containsKey(Object obj) {
        return obj instanceof String && bean.metaBean().metaPropertyExists(obj.toString());
    }

    @Override
    public Property<?> get(Object obj) {
        return containsKey(obj) ? bean.metaBean().metaProperty(obj.toString()).createProperty(bean) : null;
    }

    @Override
    public Set<String> keySet() {
        return bean.metaBean().metaPropertyMap().keySet();
    }

    @Override
    public Set<Entry<String, Property<?>>> entrySet() {
        return new AbstractSet<>() {

            @Override
            public boolean contains(Object o) {
                return super.contains(o);
            }

            @Override
            public int size() {
                return bean.metaBean().metaPropertyCount();
            }

            @Override
            public Iterator<Entry<String, Property<?>>> iterator() {
                final Iterator<MetaProperty<?>> it = bean.metaBean().metaPropertyMap().values().iterator();
                return new Iterator<Entry<String, Property<?>>>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public Entry<String, Property<?>> next() {
                        MetaProperty<?> meta = it.next();
                        return new SimpleImmutableEntry<>(meta.name(), StandardProperty.of(bean, meta));
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Unmodifiable");
                    }
                };
            }
        };
    }

    /**
     * Flattens the contents of this property map to a {@code Map}.
     * <p>
     * The returned map will contain all the properties from the bean with their actual values.
     * @return the unmodifiable map of property name to value, not null
     */
    public Map<String, Object> flatten() {
        return JodaBeanUtils.flatten(bean);
    }

}
