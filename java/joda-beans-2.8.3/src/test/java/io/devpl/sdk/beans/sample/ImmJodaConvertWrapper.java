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
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Wraps {@code ImmJodaConvertBean}, used for testing.
 */
@BeanDefinition(factoryName = "of")
public final class ImmJodaConvertWrapper implements ImmutableBean {

    /**
     * The base value.
     */
    @PropertyDefinition
    private final ImmJodaConvertBean bean;
    /**
     * The extra value.
     */
    @PropertyDefinition
    private final String description;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmJodaConvertWrapper}.
     * @return the meta-bean, not null
     */
    public static ImmJodaConvertWrapper.Meta meta() {
        return ImmJodaConvertWrapper.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmJodaConvertWrapper.Meta.INSTANCE);
    }

    /**
     * Obtains an instance.
     * @param bean  the value of the property
     * @param description  the value of the property
     * @return the instance
     */
    public static ImmJodaConvertWrapper of(
            ImmJodaConvertBean bean,
            String description) {
        return new ImmJodaConvertWrapper(
            bean,
            description);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ImmJodaConvertWrapper.Builder builder() {
        return new ImmJodaConvertWrapper.Builder();
    }

    private ImmJodaConvertWrapper(
            ImmJodaConvertBean bean,
            String description) {
        this.bean = bean;
        this.description = description;
    }

    @Override
    public ImmJodaConvertWrapper.Meta metaBean() {
        return ImmJodaConvertWrapper.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the base value.
     * @return the value of the property
     */
    public ImmJodaConvertBean getBean() {
        return bean;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the extra value.
     * @return the value of the property
     */
    public String getDescription() {
        return description;
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
            ImmJodaConvertWrapper other = (ImmJodaConvertWrapper) obj;
            return JodaBeanUtils.equal(bean, other.bean) &&
                    JodaBeanUtils.equal(description, other.description);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(bean);
        hash = hash * 31 + JodaBeanUtils.hashCode(description);
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmJodaConvertWrapper{" +
                "bean" + '=' + JodaBeanUtils.toString(bean) + ',' + ' ' +
                "description" + '=' + JodaBeanUtils.toString(description) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmJodaConvertWrapper}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code bean} property.
         */
        private final MetaProperty<ImmJodaConvertBean> bean = DirectMetaProperty.ofImmutable(
                this, "bean", ImmJodaConvertWrapper.class, ImmJodaConvertBean.class);
        /**
         * The meta-property for the {@code description} property.
         */
        private final MetaProperty<String> description = DirectMetaProperty.ofImmutable(
                this, "description", ImmJodaConvertWrapper.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "bean",
                "description");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3019696:  // bean
                    return bean;
                case -1724546052:  // description
                    return description;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmJodaConvertWrapper.Builder builder() {
            return new ImmJodaConvertWrapper.Builder();
        }

        @Override
        public Class<? extends ImmJodaConvertWrapper> beanType() {
            return ImmJodaConvertWrapper.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code bean} property.
         * @return the meta-property, not null
         */
        public MetaProperty<ImmJodaConvertBean> bean() {
            return bean;
        }

        /**
         * The meta-property for the {@code description} property.
         * @return the meta-property, not null
         */
        public MetaProperty<String> description() {
            return description;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3019696:  // bean
                    return ((ImmJodaConvertWrapper) bean).getBean();
                case -1724546052:  // description
                    return ((ImmJodaConvertWrapper) bean).getDescription();
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
     * The bean-builder for {@code ImmJodaConvertWrapper}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<ImmJodaConvertWrapper> {

        private ImmJodaConvertBean bean;
        private String description;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(ImmJodaConvertWrapper beanToCopy) {
            this.bean = beanToCopy.getBean();
            this.description = beanToCopy.getDescription();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3019696:  // bean
                    return bean;
                case -1724546052:  // description
                    return description;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 3019696:  // bean
                    this.bean = (ImmJodaConvertBean) newValue;
                    break;
                case -1724546052:  // description
                    this.description = (String) newValue;
                    break;
                default:
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
        public ImmJodaConvertWrapper build() {
            return new ImmJodaConvertWrapper(
                    bean,
                    description);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the base value.
         * @param bean  the new value
         * @return this, for chaining, not null
         */
        public Builder bean(ImmJodaConvertBean bean) {
            this.bean = bean;
            return this;
        }

        /**
         * Sets the extra value.
         * @param description  the new value
         * @return this, for chaining, not null
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmJodaConvertWrapper.Builder{" +
                    "bean" + '=' + JodaBeanUtils.toString(bean) + ',' + ' ' +
                    "description" + '=' + JodaBeanUtils.toString(description) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
