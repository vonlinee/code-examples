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
 * Mock immutable bean, used for testing deserialization.
 */
@BeanDefinition
public final class ImmDoubleFloat implements ImmutableBean {
    
    @PropertyDefinition
    private final double a;
    @PropertyDefinition
    private final double b;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmDoubleFloat}.
     * @return the meta-bean, not null
     */
    public static ImmDoubleFloat.Meta meta() {
        return ImmDoubleFloat.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmDoubleFloat.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ImmDoubleFloat.Builder builder() {
        return new ImmDoubleFloat.Builder();
    }

    private ImmDoubleFloat(
            double a,
            double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public ImmDoubleFloat.Meta metaBean() {
        return ImmDoubleFloat.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the a.
     * @return the value of the property
     */
    public double getA() {
        return a;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the b.
     * @return the value of the property
     */
    public double getB() {
        return b;
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
            ImmDoubleFloat other = (ImmDoubleFloat) obj;
            return JodaBeanUtils.equal(a, other.a) &&
                    JodaBeanUtils.equal(b, other.b);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(a);
        hash = hash * 31 + JodaBeanUtils.hashCode(b);
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmDoubleFloat{" +
                "a" + '=' + JodaBeanUtils.toString(a) + ',' + ' ' +
                "b" + '=' + JodaBeanUtils.toString(b) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmDoubleFloat}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code a} property.
         */
        private final MetaProperty<Double> a = DirectMetaProperty.ofImmutable(
                this, "a", ImmDoubleFloat.class, Double.TYPE);
        /**
         * The meta-property for the {@code b} property.
         */
        private final MetaProperty<Double> b = DirectMetaProperty.ofImmutable(
                this, "b", ImmDoubleFloat.class, Double.TYPE);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "a",
                "b");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 97:  // a
                    return a;
                case 98:  // b
                    return b;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmDoubleFloat.Builder builder() {
            return new ImmDoubleFloat.Builder();
        }

        @Override
        public Class<? extends ImmDoubleFloat> beanType() {
            return ImmDoubleFloat.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code a} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Double> a() {
            return a;
        }

        /**
         * The meta-property for the {@code b} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Double> b() {
            return b;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 97:  // a
                    return ((ImmDoubleFloat) bean).getA();
                case 98:  // b
                    return ((ImmDoubleFloat) bean).getB();
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
     * The bean-builder for {@code ImmDoubleFloat}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<ImmDoubleFloat> {

        private double a;
        private double b;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(ImmDoubleFloat beanToCopy) {
            this.a = beanToCopy.getA();
            this.b = beanToCopy.getB();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 97:  // a
                    return a;
                case 98:  // b
                    return b;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 97:  // a
                    this.a = (Double) newValue;
                    break;
                case 98:  // b
                    this.b = (Double) newValue;
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
        public ImmDoubleFloat build() {
            return new ImmDoubleFloat(
                    a,
                    b);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the a.
         * @param a  the new value
         * @return this, for chaining, not null
         */
        public Builder a(double a) {
            this.a = a;
            return this;
        }

        /**
         * Sets the b.
         * @param b  the new value
         * @return this, for chaining, not null
         */
        public Builder b(double b) {
            this.b = b;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmDoubleFloat.Builder{" +
                    "a" + '=' + JodaBeanUtils.toString(a) + ',' + ' ' +
                    "b" + '=' + JodaBeanUtils.toString(b) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
