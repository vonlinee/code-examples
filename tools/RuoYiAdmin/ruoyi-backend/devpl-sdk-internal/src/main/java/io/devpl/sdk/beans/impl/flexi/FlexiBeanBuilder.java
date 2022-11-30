package io.devpl.sdk.beans.impl.flexi;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Implementation of a meta-bean for {@code FlexiBean}.
 */
class FlexiBeanBuilder implements BeanBuilder<FlexiBean> {

    /**
     * The bean itself.
     */
    private final FlexiBean bean;

    /**
     * Creates the meta-bean.
     * 
     * @param bean  the underlying bean, not null
     */
    FlexiBeanBuilder(FlexiBean bean) {
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
    public FlexiBeanBuilder set(String propertyName, Object value) {
        bean.put(propertyName, value);
        return this;
    }

    @Override
    public FlexiBeanBuilder set(MetaProperty<?> metaProperty, Object value) {
        // this approach allows meta-property from one bean to be used with another
        bean.put(metaProperty.name(), value);
        return this;
    }

    @Override
    public FlexiBean build() {
        return bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return "FlexiBeanBuilder";
    }

}
