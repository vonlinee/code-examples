/*
 *  Copyright 2001-present Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.devpl.sdk.beans.sample;

import java.util.Map;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.ImmutableBean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;

/**
 * Immutable bean with nested generic types, used for testing.
 */
@BeanDefinition
public class ImmGenericCollections<T> implements ImmutableBean {

    @PropertyDefinition(validate = "notNull")
    private final ImmutableMap<String, T> map;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmGenericCollections}.
     * @return the meta-bean, not null
     */
    @SuppressWarnings("rawtypes")
    public static ImmGenericCollections.Meta meta() {
        return ImmGenericCollections.Meta.INSTANCE;
    }

    /**
     * The meta-bean for {@code ImmGenericCollections}.
     * @param <R>  the bean's generic type
     * @param cls  the bean's generic type
     * @return the meta-bean, not null
     */
    @SuppressWarnings("unchecked")
    public static <R> ImmGenericCollections.Meta<R> metaImmGenericCollections(Class<R> cls) {
        return ImmGenericCollections.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmGenericCollections.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @param <T>  the type
     * @return the builder, not null
     */
    public static <T> ImmGenericCollections.Builder<T> builder() {
        return new ImmGenericCollections.Builder<>();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    protected ImmGenericCollections(ImmGenericCollections.Builder<T> builder) {
        JodaBeanUtils.notNull(builder.map, "map");
        this.map = ImmutableMap.copyOf(builder.map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ImmGenericCollections.Meta<T> metaBean() {
        return ImmGenericCollections.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the map.
     * @return the value of the property, not null
     */
    public ImmutableMap<String, T> getMap() {
        return map;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a builder that allows this bean to be mutated.
     * @return the mutable builder, not null
     */
    public Builder<T> toBuilder() {
        return new Builder<>(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ImmGenericCollections<?> other = (ImmGenericCollections<?>) obj;
            return JodaBeanUtils.equal(map, other.map);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(map);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append("ImmGenericCollections{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("map").append('=').append(JodaBeanUtils.toString(map)).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmGenericCollections}.
     * @param <T>  the type
     */
    public static class Meta<T> extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        @SuppressWarnings("rawtypes")
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code map} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<ImmutableMap<String, T>> map = DirectBeanMetaProperty.ofImmutable(
                this, "map", ImmGenericCollections.class, (Class) ImmutableMap.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "map");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            if (propertyName.hashCode() == 107868) {  // map
                return map;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmGenericCollections.Builder<T> builder() {
            return new ImmGenericCollections.Builder<>();
        }

        @SuppressWarnings({"unchecked", "rawtypes" })
        @Override
        public Class<? extends ImmGenericCollections<T>> beanType() {
            return (Class) ImmGenericCollections.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code map} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<ImmutableMap<String, T>> map() {
            return map;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            if (propertyName.hashCode() == 107868) {  // map
                return ((ImmGenericCollections<?>) bean).getMap();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            metaProperty(propertyName);
            if (quiet) {
                return;
            }
            throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
        }

    }

    //-----------------------------------------------------------------------
    /**
     * The bean-builder for {@code ImmGenericCollections}.
     * @param <T>  the type
     */
    public static class Builder<T> extends DirectFieldsBeanBuilder<ImmGenericCollections<T>> {

        private Map<String, T> map = ImmutableMap.of();

        /**
         * Restricted constructor.
         */
        protected Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        protected Builder(ImmGenericCollections<T> beanToCopy) {
            this.map = beanToCopy.getMap();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            if (propertyName.hashCode() == 107868) {  // map
                return map;
            }
            throw new NoSuchElementException("Unknown property: " + propertyName);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder<T> set(String propertyName, Object newValue) {
            if (propertyName.hashCode() == 107868) {  // map
                this.map = (Map<String, T>) newValue;
            } else {
                throw new NoSuchElementException("Unknown property: " + propertyName);
            }
            return this;
        }

        @Override
        public Builder<T> set(MetaProperty<?> property, Object value) {
            super.set(property, value);
            return this;
        }

        @Override
        public ImmGenericCollections<T> build() {
            return new ImmGenericCollections<>(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the map.
         * @param map  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder<T> map(Map<String, T> map) {
            JodaBeanUtils.notNull(map, "map");
            this.map = map;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(64);
            buf.append("ImmGenericCollections.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        protected void toString(StringBuilder buf) {
            buf.append("map").append('=').append(JodaBeanUtils.toString(map)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
