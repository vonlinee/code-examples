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

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.direct.DirectBean;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public abstract class Wrapper<T extends Address> extends DirectBean {

    /** The type. */
    @PropertyDefinition
    private String type;
    /** The surname. */
    @PropertyDefinition
    private T content;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code Wrapper}.
     * @return the meta-bean, not null
     */
    @SuppressWarnings("rawtypes")
    public static Wrapper.Meta meta() {
        return Wrapper.Meta.INSTANCE;
    }

    /**
     * The meta-bean for {@code Wrapper}.
     * @param <R>  the bean's generic type
     * @param cls  the bean's generic type
     * @return the meta-bean, not null
     */
    @SuppressWarnings("unchecked")
    public static <R extends Address> Wrapper.Meta<R> metaWrapper(Class<R> cls) {
        return Wrapper.Meta.INSTANCE;
    }

    static {
        MetaBean.register(Wrapper.Meta.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Wrapper.Meta<T> metaBean() {
        return Wrapper.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type.
     * @return the value of the property
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type  the new value of the property
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the the {@code type} property.
     * @return the property, not null
     */
    public final Property<String> type() {
        return metaBean().type().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the surname.
     * @return the value of the property
     */
    public T getContent() {
        return content;
    }

    /**
     * Sets the surname.
     * @param content  the new value of the property
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * Gets the the {@code content} property.
     * @return the property, not null
     */
    public final Property<T> content() {
        return metaBean().content().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public Wrapper<T> clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            Wrapper<?> other = (Wrapper<?>) obj;
            return JodaBeanUtils.equal(getType(), other.getType()) &&
                    JodaBeanUtils.equal(getContent(), other.getContent());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getType());
        hash = hash * 31 + JodaBeanUtils.hashCode(getContent());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(96);
        buf.append("Wrapper{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("type").append('=').append(JodaBeanUtils.toString(getType())).append(',').append(' ');
        buf.append("content").append('=').append(JodaBeanUtils.toString(getContent())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code Wrapper}.
     * @param <T>  the type
     */
    public static class Meta<T extends Address> extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        @SuppressWarnings("rawtypes")
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code type} property.
         */
        private final MetaProperty<String> type = DirectBeanMetaProperty.ofReadWrite(
                this, "type", Wrapper.class, String.class);
        /**
         * The meta-property for the {@code content} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<T> content = (DirectBeanMetaProperty) DirectBeanMetaProperty.ofReadWrite(
                this, "content", Wrapper.class, Object.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "type",
                "content");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 3575610:  // type
                    return type;
                case 951530617:  // content
                    return content;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public boolean isBuildable() {
            return false;
        }

        @Override
        public BeanBuilder<? extends Wrapper<T>> builder() {
            throw new UnsupportedOperationException("Wrapper is an abstract class");
        }

        @SuppressWarnings({"unchecked", "rawtypes" })
        @Override
        public Class<? extends Wrapper<T>> beanType() {
            return (Class) Wrapper.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code type} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> type() {
            return type;
        }

        /**
         * The meta-property for the {@code content} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<T> content() {
            return content;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3575610:  // type
                    return ((Wrapper<?>) bean).getType();
                case 951530617:  // content
                    return ((Wrapper<?>) bean).getContent();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 3575610:  // type
                    ((Wrapper<T>) bean).setType((String) newValue);
                    return;
                case 951530617:  // content
                    ((Wrapper<T>) bean).setContent((T) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
