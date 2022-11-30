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
import io.devpl.sdk.beans.impl.direct.*;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;

/**
 * Mock pair, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class Pair  extends  DirectBean {
    // extra spaces in definition

    /**
     * The first value.
     */
    @PropertyDefinition
    private Object first;
    /**
     * The second value.
     */
    @PropertyDefinition
    private Object second;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code Pair}.
     * @return the meta-bean, not null
     */
    public static Pair.Meta meta() {
        return Pair.Meta.INSTANCE;
    }

    static {
        MetaBean.register(Pair.Meta.INSTANCE);
    }

    @Override
    public Pair.Meta metaBean() {
        return Pair.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the first value.
     * @return the value of the property
     */
    public Object getFirst() {
        return first;
    }

    /**
     * Sets the first value.
     * @param first  the new value of the property
     */
    public void setFirst(Object first) {
        this.first = first;
    }

    /**
     * Gets the the {@code first} property.
     * @return the property, not null
     */
    public final Property<Object> first() {
        return metaBean().first().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the second value.
     * @return the value of the property
     */
    public Object getSecond() {
        return second;
    }

    /**
     * Sets the second value.
     * @param second  the new value of the property
     */
    public void setSecond(Object second) {
        this.second = second;
    }

    /**
     * Gets the the {@code second} property.
     * @return the property, not null
     */
    public final Property<Object> second() {
        return metaBean().second().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public Pair clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            Pair other = (Pair) obj;
            return JodaBeanUtils.equal(getFirst(), other.getFirst()) &&
                    JodaBeanUtils.equal(getSecond(), other.getSecond());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getFirst());
        hash = hash * 31 + JodaBeanUtils.hashCode(getSecond());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(96);
        buf.append("Pair{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("first").append('=').append(JodaBeanUtils.toString(getFirst())).append(',').append(' ');
        buf.append("second").append('=').append(JodaBeanUtils.toString(getSecond())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code Pair}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code first} property.
         */
        private final MetaProperty<Object> first = DirectBeanMetaProperty.ofReadWrite(
                this, "first", Pair.class, Object.class);
        /**
         * The meta-property for the {@code second} property.
         */
        private final MetaProperty<Object> second = DirectBeanMetaProperty.ofReadWrite(
                this, "second", Pair.class, Object.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "first",
                "second");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 97440432:  // first
                    return first;
                case -906279820:  // second
                    return second;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends Pair> builder() {
            return new DirectBeanBuilder<>(new Pair());
        }

        @Override
        public Class<? extends Pair> beanType() {
            return Pair.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code first} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Object> first() {
            return first;
        }

        /**
         * The meta-property for the {@code second} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Object> second() {
            return second;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 97440432:  // first
                    return ((Pair) bean).getFirst();
                case -906279820:  // second
                    return ((Pair) bean).getSecond();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 97440432:  // first
                    ((Pair) bean).setFirst(newValue);
                    return;
                case -906279820:  // second
                    ((Pair) bean).setSecond(newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
