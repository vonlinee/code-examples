package io.devpl.sdk.beans.impl.map;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.PropertyStyle;
import io.devpl.sdk.beans.impl.StandardMetaProperty;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

/**
 * A meta-property using a {@code MapBean} for storage.
 */
final class MapBeanMetaProperty extends StandardMetaProperty<Object> {

    /**
     * The meta-bean.
     */
    private final MetaBean metaBean;

    /**
     * Factory to create a meta-property.
     * @param metaBean     the meta-bean, not null
     * @param propertyName the property name, not empty
     */
    static MapBeanMetaProperty of(MetaBean metaBean, String propertyName) {
        return new MapBeanMetaProperty(metaBean, propertyName);
    }

    /**
     * Constructor.
     * @param metaBean     the meta-bean, not null
     * @param propertyName the property name, not empty
     */
    private MapBeanMetaProperty(MetaBean metaBean, String propertyName) {
        super(propertyName);
        this.metaBean = metaBean;
    }

    //-----------------------------------------------------------------------
    @Override
    public MetaBean metaBean() {
        return metaBean;
    }

    @Override
    public Class<?> declaringType() {
        return BeanMap.class;
    }

    @Override
    public Class<Object> propertyType() {
        return Object.class;
    }

    @Override
    public Class<Object> propertyGenericType() {
        return Object.class;
    }

    @Override
    public PropertyStyle style() {
        return PropertyStyle.READ_WRITE;
    }

    @Override
    public List<Annotation> annotations() {
        return Collections.emptyList();
    }

    @Override
    public Object get(Bean bean) {
        return ((BeanMap) bean).get(name());
    }

    @Override
    public void set(Bean bean, Object value) {
        ((BeanMap) bean).put(name(), value);
    }

}
