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
import io.devpl.sdk.beans.impl.direct.DirectBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class PersonDocumentation extends Documentation<Person> {

    /** The name. */
    @PropertyDefinition
    private String name;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code PersonDocumentation}.
     * @return the meta-bean, not null
     */
    public static PersonDocumentation.Meta meta() {
        return PersonDocumentation.Meta.INSTANCE;
    }

    static {
        MetaBean.register(PersonDocumentation.Meta.INSTANCE);
    }

    @Override
    public PersonDocumentation.Meta metaBean() {
        return PersonDocumentation.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the name.
     * @return the value of the property
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name  the new value of the property
     */
    public void setName(String name) {
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
    @Override
    public PersonDocumentation clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            PersonDocumentation other = (PersonDocumentation) obj;
            return JodaBeanUtils.equal(getName(), other.getName()) &&
                    super.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + JodaBeanUtils.hashCode(getName());
        return hash ^ super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append("PersonDocumentation{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    @Override
    protected void toString(StringBuilder buf) {
        super.toString(buf);
        buf.append("name").append('=').append(JodaBeanUtils.toString(getName())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code PersonDocumentation}.
     */
    public static class Meta extends Documentation.Meta<Person> {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code name} property.
         */
        private final MetaProperty<String> name = DirectBeanMetaProperty.ofReadWrite(
                this, "name", PersonDocumentation.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, (DirectMetaPropertyMap) super.metaPropertyMap(),
                "name");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            if (propertyName.hashCode() == 3373707) {  // name
                return name;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends PersonDocumentation> builder() {
            return new DirectBeanBuilder<>(new PersonDocumentation());
        }

        @Override
        public Class<? extends PersonDocumentation> beanType() {
            return PersonDocumentation.class;
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

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            if (propertyName.hashCode() == 3373707) {  // name
                return ((PersonDocumentation) bean).getName();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            if (propertyName.hashCode() == 3373707) {  // name
                ((PersonDocumentation) bean).setName((String) newValue);
                return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}