package io.devpl.sdk.beans.impl.flexi;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.PropertyStyle;
import io.devpl.sdk.beans.impl.StandardMetaProperty;

/**
 * A meta-property using a {@code FlexiBean} for storage.
 */
final class FlexiBeanMetaProperty extends StandardMetaProperty<Object> {

    /**
     * The meta-bean.
     */
    private final MetaBean metaBean;

    /**
     * Factory to create a meta-property.
     * @param metaBean     the meta-bean, not null
     * @param propertyName the property name, not empty
     */
    static FlexiBeanMetaProperty of(MetaBean metaBean, String propertyName) {
        return new FlexiBeanMetaProperty(metaBean, propertyName);
    }

    /**
     * Constructor.
     * @param metaBean     the meta-bean, not null
     * @param propertyName the property name, not empty
     */
    private FlexiBeanMetaProperty(MetaBean metaBean, String propertyName) {
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
        return FlexiBean.class;
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
    public Object get(Bean bean) throws IllegalAccessException {
        if (bean instanceof FlexiBean) {
            return ((FlexiBean) bean).propertyGet(name());
        }
        throw new IllegalAccessException();
    }

    @Override
    public void set(Bean bean, Object value) {
        ((FlexiBean) bean).propertySet(name(), value);
    }
}
