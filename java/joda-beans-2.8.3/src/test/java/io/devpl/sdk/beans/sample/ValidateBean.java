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
import io.devpl.sdk.beans.TestValidateBean;
import io.devpl.sdk.beans.impl.direct.DirectBean;
import io.devpl.sdk.beans.impl.direct.DirectBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock used for test equals.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class ValidateBean extends DirectBean {

    /**
     * The non-null value.
     */
    @PropertyDefinition(validate = "notNull")
    private String first;
    /**
     * The non-empty value.
     */
    @PropertyDefinition(validate = "notEmpty")
    private String second;
    /**
     * The static checked value.
     */
    @PropertyDefinition(validate = "TestValidateBean.checkInTest")
    private String third;
    /**
     * The locally checked value.
     */
    @PropertyDefinition(validate = "checkInBean")
    private String fourth;
    /**
     * The non-blank value.
     */
    @PropertyDefinition(validate = "notBlank")
    private String fifth;

    public ValidateBean() {
    }

    private static void checkInBean(String value, String propertyName) {
        if (!"D".equals(value)) {
            throw new IllegalArgumentException(propertyName);
        }
    }

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ValidateBean}.
     * @return the meta-bean, not null
     */
    public static ValidateBean.Meta meta() {
        return ValidateBean.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ValidateBean.Meta.INSTANCE);
    }

    @Override
    public ValidateBean.Meta metaBean() {
        return ValidateBean.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the non-null value.
     * @return the value of the property, not null
     */
    public String getFirst() {
        return first;
    }

    /**
     * Sets the non-null value.
     * @param first  the new value of the property, not null
     */
    public void setFirst(String first) {
        JodaBeanUtils.notNull(first, "first");
        this.first = first;
    }

    /**
     * Gets the the {@code first} property.
     * @return the property, not null
     */
    public final Property<String> first() {
        return metaBean().first().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the non-empty value.
     * @return the value of the property, not empty
     */
    public String getSecond() {
        return second;
    }

    /**
     * Sets the non-empty value.
     * @param second  the new value of the property, not empty
     */
    public void setSecond(String second) {
        JodaBeanUtils.notEmpty(second, "second");
        this.second = second;
    }

    /**
     * Gets the the {@code second} property.
     * @return the property, not null
     */
    public final Property<String> second() {
        return metaBean().second().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the static checked value.
     * @return the value of the property
     */
    public String getThird() {
        return third;
    }

    /**
     * Sets the static checked value.
     * @param third  the new value of the property
     */
    public void setThird(String third) {
        TestValidateBean.checkInTest(third, "third");
        this.third = third;
    }

    /**
     * Gets the the {@code third} property.
     * @return the property, not null
     */
    public final Property<String> third() {
        return metaBean().third().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the locally checked value.
     * @return the value of the property
     */
    public String getFourth() {
        return fourth;
    }

    /**
     * Sets the locally checked value.
     * @param fourth  the new value of the property
     */
    public void setFourth(String fourth) {
        checkInBean(fourth, "fourth");
        this.fourth = fourth;
    }

    /**
     * Gets the the {@code fourth} property.
     * @return the property, not null
     */
    public final Property<String> fourth() {
        return metaBean().fourth().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the non-blank value.
     * @return the value of the property, not blank
     */
    public String getFifth() {
        return fifth;
    }

    /**
     * Sets the non-blank value.
     * @param fifth  the new value of the property, not blank
     */
    public void setFifth(String fifth) {
        JodaBeanUtils.notBlank(fifth, "fifth");
        this.fifth = fifth;
    }

    /**
     * Gets the the {@code fifth} property.
     * @return the property, not null
     */
    public final Property<String> fifth() {
        return metaBean().fifth().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public ValidateBean clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ValidateBean other = (ValidateBean) obj;
            return JodaBeanUtils.equal(getFirst(), other.getFirst()) &&
                    JodaBeanUtils.equal(getSecond(), other.getSecond()) &&
                    JodaBeanUtils.equal(getThird(), other.getThird()) &&
                    JodaBeanUtils.equal(getFourth(), other.getFourth()) &&
                    JodaBeanUtils.equal(getFifth(), other.getFifth());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getFirst());
        hash = hash * 31 + JodaBeanUtils.hashCode(getSecond());
        hash = hash * 31 + JodaBeanUtils.hashCode(getThird());
        hash = hash * 31 + JodaBeanUtils.hashCode(getFourth());
        hash = hash * 31 + JodaBeanUtils.hashCode(getFifth());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(192);
        buf.append("ValidateBean{");
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
        buf.append("third").append('=').append(JodaBeanUtils.toString(getThird())).append(',').append(' ');
        buf.append("fourth").append('=').append(JodaBeanUtils.toString(getFourth())).append(',').append(' ');
        buf.append("fifth").append('=').append(JodaBeanUtils.toString(getFifth())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ValidateBean}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code first} property.
         */
        private final MetaProperty<String> first = DirectMetaProperty.ofReadWrite(
                this, "first", ValidateBean.class, String.class);
        /**
         * The meta-property for the {@code second} property.
         */
        private final MetaProperty<String> second = DirectMetaProperty.ofReadWrite(
                this, "second", ValidateBean.class, String.class);
        /**
         * The meta-property for the {@code third} property.
         */
        private final MetaProperty<String> third = DirectMetaProperty.ofReadWrite(
                this, "third", ValidateBean.class, String.class);
        /**
         * The meta-property for the {@code fourth} property.
         */
        private final MetaProperty<String> fourth = DirectMetaProperty.ofReadWrite(
                this, "fourth", ValidateBean.class, String.class);
        /**
         * The meta-property for the {@code fifth} property.
         */
        private final MetaProperty<String> fifth = DirectMetaProperty.ofReadWrite(
                this, "fifth", ValidateBean.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "first",
                "second",
                "third",
                "fourth",
                "fifth");

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
                case 110331239:  // third
                    return third;
                case -1268684262:  // fourth
                    return fourth;
                case 97428919:  // fifth
                    return fifth;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends ValidateBean> builder() {
            return new DirectBeanBuilder<>(new ValidateBean());
        }

        @Override
        public Class<? extends ValidateBean> beanType() {
            return ValidateBean.class;
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
        public final MetaProperty<String> first() {
            return first;
        }

        /**
         * The meta-property for the {@code second} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> second() {
            return second;
        }

        /**
         * The meta-property for the {@code third} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> third() {
            return third;
        }

        /**
         * The meta-property for the {@code fourth} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> fourth() {
            return fourth;
        }

        /**
         * The meta-property for the {@code fifth} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> fifth() {
            return fifth;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 97440432:  // first
                    return ((ValidateBean) bean).getFirst();
                case -906279820:  // second
                    return ((ValidateBean) bean).getSecond();
                case 110331239:  // third
                    return ((ValidateBean) bean).getThird();
                case -1268684262:  // fourth
                    return ((ValidateBean) bean).getFourth();
                case 97428919:  // fifth
                    return ((ValidateBean) bean).getFifth();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 97440432:  // first
                    ((ValidateBean) bean).setFirst((String) newValue);
                    return;
                case -906279820:  // second
                    ((ValidateBean) bean).setSecond((String) newValue);
                    return;
                case 110331239:  // third
                    ((ValidateBean) bean).setThird((String) newValue);
                    return;
                case -1268684262:  // fourth
                    ((ValidateBean) bean).setFourth((String) newValue);
                    return;
                case 97428919:  // fifth
                    ((ValidateBean) bean).setFifth((String) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notNull(((ValidateBean) bean).first, "first");
            JodaBeanUtils.notEmpty(((ValidateBean) bean).second, "second");
            TestValidateBean.checkInTest(((ValidateBean) bean).third, "third");
            checkInBean(((ValidateBean) bean).fourth, "fourth");
            JodaBeanUtils.notBlank(((ValidateBean) bean).fifth, "fifth");
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
