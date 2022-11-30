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
* Mock bean for complex annotation testing.
*/
@BeanDefinition(cacheHashCode = true, factoryName = "create")
@ClassAnnotation(ImmComplexAnnotation.class)
@ComplexAnnotation({
    @SimpleAnnotation(first = "1", second = "2", third = "3"),
    @SimpleAnnotation(first = "1", second = "2", third = "3")
}) 
public final class ImmComplexAnnotation
      implements ImmutableBean,
      Cloneable,
      Serializable {

    @PropertyDefinition
    private final double value;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmComplexAnnotation}.
     * @return the meta-bean, not null
     */
    public static ImmComplexAnnotation.Meta meta() {
        return ImmComplexAnnotation.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ImmComplexAnnotation.Meta.INSTANCE);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The cached hash code, using the racy single-check idiom.
     */
    private transient int cacheHashCode;

    /**
     * Obtains an instance.
     * @param value  the value of the property
     * @return the instance
     */
    public static ImmComplexAnnotation create(
            double value) {
        return new ImmComplexAnnotation(
            value);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ImmComplexAnnotation.Builder builder() {
        return new ImmComplexAnnotation.Builder();
    }

    private ImmComplexAnnotation(
            double value) {
        this.value = value;
    }

    @Override
    public ImmComplexAnnotation.Meta metaBean() {
        return ImmComplexAnnotation.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value.
     * @return the value of the property
     */
    public double getValue() {
        return value;
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
            ImmComplexAnnotation other = (ImmComplexAnnotation) obj;
            return JodaBeanUtils.equal(value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = cacheHashCode;
        if (hash == 0) {
            hash = getClass().hashCode();
            hash = hash * 31 + JodaBeanUtils.hashCode(value);
            cacheHashCode = hash;
        }
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmComplexAnnotation{" +
                "value" + '=' + JodaBeanUtils.toString(value) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ImmComplexAnnotation}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code value} property.
         */
        private final MetaProperty<Double> value = DirectMetaProperty.ofImmutable(
                this, "value", ImmComplexAnnotation.class, Double.TYPE);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "value");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            if (propertyName.hashCode() == 111972721) {  // value
                return value;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ImmComplexAnnotation.Builder builder() {
            return new ImmComplexAnnotation.Builder();
        }

        @Override
        public Class<? extends ImmComplexAnnotation> beanType() {
            return ImmComplexAnnotation.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code value} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Double> value() {
            return value;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            if (propertyName.hashCode() == 111972721) {  // value
                return ((ImmComplexAnnotation) bean).getValue();
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
     * The bean-builder for {@code ImmComplexAnnotation}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<ImmComplexAnnotation> {

        private double value;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(ImmComplexAnnotation beanToCopy) {
            this.value = beanToCopy.getValue();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            if (propertyName.hashCode() == 111972721) {  // value
                return value;
            }
            throw new NoSuchElementException("Unknown property: " + propertyName);
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            if (propertyName.hashCode() == 111972721) {  // value
                this.value = (Double) newValue;
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
        public ImmComplexAnnotation build() {
            return new ImmComplexAnnotation(
                    value);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the value.
         * @param value  the new value
         * @return this, for chaining, not null
         */
        public Builder value(double value) {
            this.value = value;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmComplexAnnotation.Builder{" +
                    "value" + '=' + JodaBeanUtils.toString(value) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
