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
import java.util.Arrays;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.TypedMetaBean;
import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.impl.StandardBeanBuilder;
import io.devpl.sdk.beans.impl.direct.MinimalMetaBean;

/**
 * Mock minimal bean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition(style = "minimal")
public final class MinimalMutableGenericSimple<T extends Number> implements Bean, Serializable {

    /**
     * The number.
     */
    @PropertyDefinition
    private int number;
    /**
     * The text.
     */
    @PropertyDefinition
    private String text;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code MinimalMutableGenericSimple}.
     */
    @SuppressWarnings("rawtypes")
    private static final MetaBean META_BEAN =
            MinimalMetaBean.of(
                    MinimalMutableGenericSimple.class,
                    new String[] {
                            "number",
                            "text"},
                    () -> new StandardBeanBuilder<>(new MinimalMutableGenericSimple<>()),
                    Arrays.asList(
                            b -> b.getNumber(),
                            b -> b.getText()),
                    Arrays.asList(
                            (b, v) -> b.setNumber((Integer) v),
                            (b, v) -> b.setText((String) v)));

    /**
     * The meta-bean for {@code MinimalMutableGenericSimple}.
     * @return the meta-bean, not null
     */
    public static MetaBean meta() {
        return META_BEAN;
    }

    static {
        MetaBean.register(META_BEAN);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    public TypedMetaBean<MinimalMutableGenericSimple<T>> metaBean() {
        return (TypedMetaBean<MinimalMutableGenericSimple<T>>) META_BEAN;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number.
     * @return the value of the property
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     * @param number  the new value of the property
     */
    public void setNumber(int number) {
        this.number = number;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the text.
     * @return the value of the property
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     * @param text  the new value of the property
     */
    public void setText(String text) {
        this.text = text;
    }

    //-----------------------------------------------------------------------
    @Override
    public MinimalMutableGenericSimple<T> clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            MinimalMutableGenericSimple<?> other = (MinimalMutableGenericSimple<?>) obj;
            return (getNumber() == other.getNumber()) &&
                    JodaBeanUtils.equal(getText(), other.getText());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getNumber());
        hash = hash * 31 + JodaBeanUtils.hashCode(getText());
        return hash;
    }

    @Override
    public String toString() {
        String buf = "MinimalMutableGenericSimple{" +
                "number" + '=' + JodaBeanUtils.toString(getNumber()) + ',' + ' ' +
                "text" + '=' + JodaBeanUtils.toString(getText()) +
                '}';
        return buf;
    }

    //-------------------------- AUTOGENERATED END --------------------------
}
