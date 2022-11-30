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
import java.util.Map;

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.direct.DirectBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class GenericUnionType<T extends Number & Serializable & Cloneable> implements Bean {

    /** The name. */
    @PropertyDefinition(validate = "notNull")
    private String name;
    /** The value. */
    @PropertyDefinition(validate = "notNull")
    private T value;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code GenericUnionType}.
     * @return the meta-bean, not null
     */
    @SuppressWarnings("rawtypes")
    public static GenericUnionType.Meta meta() {
        return GenericUnionType.Meta.INSTANCE;
    }

    /**
     * The meta-bean for {@code GenericUnionType}.
     * @param <R>  the bean's generic type
     * @param cls  the bean's generic type
     * @return the meta-bean, not null
     */
    @SuppressWarnings("unchecked")
    public static <R extends Number & Serializable & Cloneable> GenericUnionType.Meta<R> metaGenericUnionType(Class<R> cls) {
        return GenericUnionType.Meta.INSTANCE;
    }

    static {
        MetaBean.register(GenericUnionType.Meta.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GenericUnionType.Meta<T> metaBean() {
        return GenericUnionType.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the name.
     * @return the value of the property, not null
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name  the new value of the property, not null
     */
    public void setName(String name) {
        JodaBeanUtils.notNull(name, "name");
        this.name = name;
    }

    /**
     * Gets the the {@code name} property.
     * @return the property, not null
     */
    public final Property<String> name() {
        return metaBean().name().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value.
     * @return the value of the property, not null
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value.
     * @param value  the new value of the property, not null
     */
    public void setValue(T value) {
        JodaBeanUtils.notNull(value, "value");
        this.value = value;
    }

    /**
     * Gets the the {@code value} property.
     * @return the property, not null
     */
    public final Property<T> value() {
        return metaBean().value().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public GenericUnionType<T> clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            GenericUnionType<?> other = (GenericUnionType<?>) obj;
            return JodaBeanUtils.equal(getName(), other.getName()) &&
                    JodaBeanUtils.equal(getValue(), other.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getName());
        hash = hash * 31 + JodaBeanUtils.hashCode(getValue());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(96);
        buf.append("GenericUnionType{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("name").append('=').append(JodaBeanUtils.toString(getName())).append(',').append(' ');
        buf.append("value").append('=').append(JodaBeanUtils.toString(getValue())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code GenericUnionType}.
     * @param <T>  the type
     */
    public static class Meta<T extends Number & Serializable & Cloneable> extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        @SuppressWarnings("rawtypes")
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code name} property.
         */
        private final MetaProperty<String> name = DirectMetaProperty.ofReadWrite(
                this, "name", GenericUnionType.class, String.class);
        /**
         * The meta-property for the {@code value} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<T> value = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
                this, "value", GenericUnionType.class, Object.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "name",
                "value");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3373707:  // name
                    return name;
                case 111972721:  // value
                    return value;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends GenericUnionType<T>> builder() {
            return new DirectBeanBuilder<>(new GenericUnionType<T>());
        }

        @SuppressWarnings({"unchecked", "rawtypes" })
        @Override
        public Class<? extends GenericUnionType<T>> beanType() {
            return (Class) GenericUnionType.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code name} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> name() {
            return name;
        }

        /**
         * The meta-property for the {@code value} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<T> value() {
            return value;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3373707:  // name
                    return ((GenericUnionType<?>) bean).getName();
                case 111972721:  // value
                    return ((GenericUnionType<?>) bean).getValue();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3373707:  // name
                    ((GenericUnionType<T>) bean).setName((String) newValue);
                    return;
                case 111972721:  // value
                    ((GenericUnionType<T>) bean).setValue((T) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notNull(((GenericUnionType<?>) bean).name, "name");
            JodaBeanUtils.notNull(((GenericUnionType<?>) bean).value, "value");
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
