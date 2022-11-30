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

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.impl.direct.DirectBean;
import io.devpl.sdk.beans.impl.direct.DirectBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock used for testing an absence of properties.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class NoProperties extends DirectBean {

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code NoProperties}.
     * @return the meta-bean, not null
     */
    public static NoProperties.Meta meta() {
        return NoProperties.Meta.INSTANCE;
    }

    static {
        MetaBean.register(NoProperties.Meta.INSTANCE);
    }

    @Override
    public NoProperties.Meta metaBean() {
        return NoProperties.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    @Override
    public NoProperties clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(32);
        buf.append("NoProperties{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code NoProperties}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null);

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        public BeanBuilder<? extends NoProperties> builder() {
            return new DirectBeanBuilder<>(new NoProperties());
        }

        @Override
        public Class<? extends NoProperties> beanType() {
            return NoProperties.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
    }

    //-------------------------- AUTOGENERATED END --------------------------
}
