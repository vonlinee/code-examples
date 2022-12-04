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
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock.
 */
@BeanDefinition(builderScope = "public")
public class MutableBaseBean implements Bean {

    @PropertyDefinition
    private String baseBeanString;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code MutableBaseBean}.
     * @return the meta-bean, not null
     */
    public static MutableBaseBean.Meta meta() {
        return MutableBaseBean.Meta.INSTANCE;
    }

    static {
        MetaBean.register(MutableBaseBean.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static MutableBaseBean.Builder builder() {
        return new MutableBaseBean.Builder();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    protected MutableBaseBean(MutableBaseBean.Builder builder) {
        this.baseBeanString = builder.baseBeanString;
    }

    @Override
    public MutableBaseBean.Meta metaBean() {
        return MutableBaseBean.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the baseBeanString.
     * @return the value of the property
     */
    public String getBaseBeanString() {
        return baseBeanString;
    }

    /**
     * Sets the baseBeanString.
     * @param baseBeanString  the new value of the property
     */
    public void setBaseBeanString(String baseBeanString) {
        this.baseBeanString = baseBeanString;
    }

    /**
     * Gets the the {@code baseBeanString} property.
     * @return the property, not null
     */
    public final Property<String> baseBeanString() {
        return metaBean().baseBeanString().createProperty(this);
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
    public MutableBaseBean clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            MutableBaseBean other = (MutableBaseBean) obj;
            return JodaBeanUtils.equal(getBaseBeanString(), other.getBaseBeanString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getBaseBeanString());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append("MutableBaseBean{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("baseBeanString").append('=').append(JodaBeanUtils.toString(getBaseBeanString())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code MutableBaseBean}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code baseBeanString} property.
         */
        private final MetaProperty<String> baseBeanString = DirectBeanMetaProperty.ofReadWrite(
                this, "baseBeanString", MutableBaseBean.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "baseBeanString");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            if (propertyName.hashCode() == 788344210) {  // baseBeanString
                return baseBeanString;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public MutableBaseBean.Builder builder() {
            return new MutableBaseBean.Builder();
        }

        @Override
        public Class<? extends MutableBaseBean> beanType() {
            return MutableBaseBean.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code baseBeanString} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> baseBeanString() {
            return baseBeanString;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            if (propertyName.hashCode() == 788344210) {  // baseBeanString
                return ((MutableBaseBean) bean).getBaseBeanString();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            if (propertyName.hashCode() == 788344210) {  // baseBeanString
                ((MutableBaseBean) bean).setBaseBeanString((String) newValue);
                return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-----------------------------------------------------------------------
    /**
     * The bean-builder for {@code MutableBaseBean}.
     */
    public static class Builder extends DirectFieldsBeanBuilder<MutableBaseBean> {

        private String baseBeanString;

        /**
         * Restricted constructor.
         */
        protected Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        protected Builder(MutableBaseBean beanToCopy) {
            this.baseBeanString = beanToCopy.getBaseBeanString();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            if (propertyName.hashCode() == 788344210) {  // baseBeanString
                return baseBeanString;
            }
            throw new NoSuchElementException("Unknown property: " + propertyName);
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            if (propertyName.hashCode() == 788344210) {  // baseBeanString
                this.baseBeanString = (String) newValue;
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
        public MutableBaseBean build() {
            return new MutableBaseBean(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the baseBeanString.
         * @param baseBeanString  the new value
         * @return this, for chaining, not null
         */
        public Builder baseBeanString(String baseBeanString) {
            this.baseBeanString = baseBeanString;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(64);
            buf.append("MutableBaseBean.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        protected void toString(StringBuilder buf) {
            buf.append("baseBeanString").append('=').append(JodaBeanUtils.toString(baseBeanString)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}