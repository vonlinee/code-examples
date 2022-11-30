package io.devpl.sdk.beans.impl.map;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Implementation of a meta-bean for {@code MapBean}.
 */
class MapBeanBuilder implements BeanBuilder<BeanMap> {

    /**
     * The bean itself.
     */
    private final BeanMap bean;

    /**
     * Creates the meta-bean.
     * @param bean the underlying bean, not null
     */
    MapBeanBuilder(BeanMap bean) {
        this.bean = bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        // lenient getter
        return bean.get(propertyName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        // this approach allows meta-property from one bean to be used with another
        return (P) bean.get(metaProperty.name());
    }

    //-----------------------------------------------------------------------
    @Override
    public MapBeanBuilder set(String propertyName, Object value) {
        bean.put(propertyName, value);
        return this;
    }

    @Override
    public MapBeanBuilder set(MetaProperty<?> metaProperty, Object value) {
        // this approach allows meta-property from one bean to be used with another
        bean.put(metaProperty.name(), value);
        return this;
    }

    @Override
    public BeanMap build() {
        return bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return "MapBeanBuilder";
    }

}
