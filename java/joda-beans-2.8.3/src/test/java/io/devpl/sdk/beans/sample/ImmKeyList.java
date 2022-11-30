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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.ImmutableBean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;

/**
 * Mock list of keys JavaBean, used for testing.
 */
@BeanDefinition(constructorScope = "private")
public final class ImmKeyList implements ImmutableBean, Serializable {

    /**
     * The keys.
     */
    @PropertyDefinition(validate = "notEmpty")
    private final ImmutableList<IKey> keys;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmKeyList}.
     * @return the meta-bean, not null
     */
    public static ImmKeyList.Meta meta() {
        return ImmKeyList.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmKeyList.Meta.INSTANCE);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ImmKeyList.Builder builder() {
        return new ImmKeyList.Builder();
    }

    private ImmKeyList(
            List<IKey> keys) {
        JodaBeanUtils.notEmpty(keys, "keys");
        this.keys = ImmutableList.copyOf(keys);
    }

    @Override
    public ImmKeyList.Meta metaBean() {
        return ImmKeyList.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the keys.
     * @return the value of the property, not empty
     */
    public ImmutableList<IKey> getKeys() {
        return keys;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a builder that allows this bean to be mutated.
     * @return the mutable builder, not null
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ImmKeyList other = (ImmKeyList) obj;
            return JodaBeanUtils.equal(keys, other.keys);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(keys);
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmKeyList{" +
                "keys" + '=' + JodaBeanUtils.toString(keys) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmKeyList}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code keys} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<ImmutableList<IKey>> keys = DirectBeanMetaProperty.ofImmutable(
                this, "keys", ImmKeyList.class, (Class) ImmutableList.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "keys");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            if (propertyName.hashCode() == 3288564) {  // keys
                return keys;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmKeyList.Builder builder() {
            return new ImmKeyList.Builder();
        }

        @Override
        public Class<? extends ImmKeyList> beanType() {
            return ImmKeyList.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code keys} property.
         * @return the meta-property, not null
         */
        public MetaProperty<ImmutableList<IKey>> keys() {
            return keys;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            if (propertyName.hashCode() == 3288564) {  // keys
                return ((ImmKeyList) bean).getKeys();
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
     * The bean-builder for {@code ImmKeyList}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<ImmKeyList> {

        private List<IKey> keys = ImmutableList.of();

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(ImmKeyList beanToCopy) {
            this.keys = beanToCopy.getKeys();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            if (propertyName.hashCode() == 3288564) {  // keys
                return keys;
            }
            throw new NoSuchElementException("Unknown property: " + propertyName);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder set(String propertyName, Object newValue) {
            if (propertyName.hashCode() == 3288564) {  // keys
                this.keys = (List<IKey>) newValue;
            } else {
                throw new NoSuchElementException("Unknown property: " + propertyName);
            }
            return this;
        }

        @Override
        public Builder set(MetaProperty<?> property, Object value) {
            super.set(property, value);
            return this;
        }

        @Override
        public ImmKeyList build() {
            return new ImmKeyList(
                    keys);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the keys.
         * @param keys  the new value, not empty
         * @return this, for chaining, not null
         */
        public Builder keys(List<IKey> keys) {
            JodaBeanUtils.notEmpty(keys, "keys");
            this.keys = keys;
            return this;
        }

        /**
         * Sets the {@code keys} property in the builder
         * from an array of objects.
         * @param keys  the new value, not empty
         * @return this, for chaining, not null
         */
        public Builder keys(IKey... keys) {
            return keys(ImmutableList.copyOf(keys));
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmKeyList.Builder{" +
                    "keys" + '=' + JodaBeanUtils.toString(keys) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
