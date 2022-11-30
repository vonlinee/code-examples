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
import org.joda.convert.FromString;
import org.joda.convert.ToString;

/**
 * An immutable bean that is also a Joda-Convert type, used for testing.
 */
@BeanDefinition
public final class ImmJodaConvertBean implements ImmutableBean {

    /**
     * The base value.
     */
    @PropertyDefinition
    private final String base;
    /**
     * The extra value.
     */
    @PropertyDefinition
    private final int extra;

    @FromString
    public ImmJodaConvertBean(String text) {
        base = text.substring(0, text.indexOf(':'));
        extra = Integer.parseInt(text.substring(text.indexOf(':') + 1));
    }

    @ToString
    public String formattedString() {
        return base + ":" + extra;
    }

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmJodaConvertBean}.
     * @return the meta-bean, not null
     */
    public static ImmJodaConvertBean.Meta meta() {
        return ImmJodaConvertBean.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmJodaConvertBean.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ImmJodaConvertBean.Builder builder() {
        return new ImmJodaConvertBean.Builder();
    }

    private ImmJodaConvertBean(
            String base,
            int extra) {
        this.base = base;
        this.extra = extra;
    }

    @Override
    public ImmJodaConvertBean.Meta metaBean() {
        return ImmJodaConvertBean.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the base value.
     * @return the value of the property
     */
    public String getBase() {
        return base;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the extra value.
     * @return the value of the property
     */
    public int getExtra() {
        return extra;
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
            ImmJodaConvertBean other = (ImmJodaConvertBean) obj;
            return JodaBeanUtils.equal(base, other.base) &&
                    (extra == other.extra);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(base);
        hash = hash * 31 + JodaBeanUtils.hashCode(extra);
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmJodaConvertBean{" +
                "base" + '=' + JodaBeanUtils.toString(base) + ',' + ' ' +
                "extra" + '=' + JodaBeanUtils.toString(extra) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmJodaConvertBean}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code base} property.
         */
        private final MetaProperty<String> base = DirectBeanMetaProperty.ofImmutable(
                this, "base", ImmJodaConvertBean.class, String.class);
        /**
         * The meta-property for the {@code extra} property.
         */
        private final MetaProperty<Integer> extra = DirectBeanMetaProperty.ofImmutable(
                this, "extra", ImmJodaConvertBean.class, Integer.TYPE);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "base",
                "extra");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3016401:  // base
                    return base;
                case 96965648:  // extra
                    return extra;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmJodaConvertBean.Builder builder() {
            return new ImmJodaConvertBean.Builder();
        }

        @Override
        public Class<? extends ImmJodaConvertBean> beanType() {
            return ImmJodaConvertBean.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code base} property.
         * @return the meta-property, not null
         */
        public MetaProperty<String> base() {
            return base;
        }

        /**
         * The meta-property for the {@code extra} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Integer> extra() {
            return extra;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3016401:  // base
                    return ((ImmJodaConvertBean) bean).getBase();
                case 96965648:  // extra
                    return ((ImmJodaConvertBean) bean).getExtra();
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
     * The bean-builder for {@code ImmJodaConvertBean}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<ImmJodaConvertBean> {

        private String base;
        private int extra;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(ImmJodaConvertBean beanToCopy) {
            this.base = beanToCopy.getBase();
            this.extra = beanToCopy.getExtra();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3016401:  // base
                    return base;
                case 96965648:  // extra
                    return extra;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 3016401:  // base
                    this.base = (String) newValue;
                    break;
                case 96965648:  // extra
                    this.extra = (Integer) newValue;
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
        public ImmJodaConvertBean build() {
            return new ImmJodaConvertBean(
                    base,
                    extra);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the base value.
         * @param base  the new value
         * @return this, for chaining, not null
         */
        public Builder base(String base) {
            this.base = base;
            return this;
        }

        /**
         * Sets the extra value.
         * @param extra  the new value
         * @return this, for chaining, not null
         */
        public Builder extra(int extra) {
            this.extra = extra;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmJodaConvertBean.Builder{" +
                    "base" + '=' + JodaBeanUtils.toString(base) + ',' + ' ' +
                    "extra" + '=' + JodaBeanUtils.toString(extra) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
