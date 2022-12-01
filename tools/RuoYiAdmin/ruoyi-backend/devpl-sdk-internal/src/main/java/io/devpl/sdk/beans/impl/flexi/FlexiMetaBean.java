package io.devpl.sdk.beans.impl.flexi;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.DynamicMetaBean;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Implementation of a meta-bean for {@code FlexiBean}.
 */
class FlexiMetaBean implements DynamicMetaBean {

    /**
     * The bean itself.
     */
    private final FlexiBean bean;

    /**
     * Creates the meta-bean.
     * 
     * @param bean  the underlying bean, not null
     */
    FlexiMetaBean(FlexiBean bean) {
        this.bean = bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isBuildable() {
        return true;
    }

    @Override
    public BeanBuilder<FlexiBean> builder() {
        return new FlexiBeanBuilder(bean);
    }

    @Override
    public Class<FlexiBean> beanType() {
        return FlexiBean.class;
    }

    @Override
    public int metaPropertyCount() {
        return bean.size();
    }

    @Override
    public boolean metaPropertyExists(String name) {
        return bean.propertyExists(name);
    }

    @Override
    public MetaProperty<Object> metaProperty(String name) {
        // do not check if exists
        return FlexiBeanMetaProperty.of(this, name);
    }

    @Override
    public Iterable<MetaProperty<?>> metaPropertyIterable() {
        if (bean.data.isEmpty()) {
            return Collections.emptySet();
        }
        return () -> {
            Iterator<String> it = bean.data.keySet().iterator();
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public MetaProperty<?> next() {
                    return FlexiBeanMetaProperty.of(FlexiMetaBean.this, it.next());
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Unmodifiable");
                }
            };
        };
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
        if (bean.data.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, MetaProperty<?>> map = new LinkedHashMap<>();
        for (String name : bean.data.keySet()) {
            map.put(name, FlexiBeanMetaProperty.of(this, name));
        }
        return Collections.unmodifiableMap(map);
    }

    //-----------------------------------------------------------------------
    @Override
    public void metaPropertyDefine(String propertyName, Class<?> propertyType) {
        bean.defineProperty(propertyName, propertyType);
    }

    @Override
    public void metaPropertyRemove(String propertyName) {
        bean.removeProperty(propertyName);
    }

    @Override
    public List<Annotation> annotations() {
        return Collections.emptyList();
    }

    /**
     * Returns a string that summarises the meta-bean.
     * 
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return "MetaBean:" + beanType().getSimpleName();
    }

}
