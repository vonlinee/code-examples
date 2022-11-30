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

import java.util.NoSuchElementException;

import io.devpl.sdk.beans.ImmutableBean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.TypedMetaBean;
import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.impl.direct.DirectPrivateBeanBuilder;
import io.devpl.sdk.beans.impl.direct.MinimalMetaBean;

/**
 * Mock address JavaBean, used for testing.
 * <p>
 * Minimal with private scope builder.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition(style = "minimal", builderScope = "private")
public final class ImmMinimalPrivateBuilder implements ImmutableBean {

    /**
     * The number.
     */
    @PropertyDefinition
    private final int number;
    /**
     * The street.
     */
    @PropertyDefinition(validate = "notNull")
    private final String street;
    /**
     * The city.
     */
    @PropertyDefinition(validate = "notNull")
    private final String city;
    /**
     * The owner.
     */
    @PropertyDefinition(validate = "notNull")
    private final ImmPerson owner;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ImmMinimalPrivateBuilder}.
     */
    private static final TypedMetaBean<ImmMinimalPrivateBuilder> META_BEAN =
            MinimalMetaBean.of(
                    ImmMinimalPrivateBuilder.class,
                    new String[] {
                            "number",
                            "street",
                            "city",
                            "owner"},
                    () -> new ImmMinimalPrivateBuilder.Builder(),
                    b -> b.getNumber(),
                    b -> b.getStreet(),
                    b -> b.getCity(),
                    b -> b.getOwner());

    /**
     * The meta-bean for {@code ImmMinimalPrivateBuilder}.
     * @return the meta-bean, not null
     */
    public static TypedMetaBean<ImmMinimalPrivateBuilder> meta() {
        return META_BEAN;
    }

    static {
        MetaBean.register(META_BEAN);
    }

    private ImmMinimalPrivateBuilder(
            int number,
            String street,
            String city,
            ImmPerson owner) {
        JodaBeanUtils.notNull(street, "street");
        JodaBeanUtils.notNull(city, "city");
        JodaBeanUtils.notNull(owner, "owner");
        this.number = number;
        this.street = street;
        this.city = city;
        this.owner = owner;
    }

    @Override
    public TypedMetaBean<ImmMinimalPrivateBuilder> metaBean() {
        return META_BEAN;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number.
     * @return the value of the property
     */
    public int getNumber() {
        return number;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the street.
     * @return the value of the property, not null
     */
    public String getStreet() {
        return street;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the city.
     * @return the value of the property, not null
     */
    public String getCity() {
        return city;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the owner.
     * @return the value of the property, not null
     */
    public ImmPerson getOwner() {
        return owner;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ImmMinimalPrivateBuilder other = (ImmMinimalPrivateBuilder) obj;
            return (number == other.number) &&
                    JodaBeanUtils.equal(street, other.street) &&
                    JodaBeanUtils.equal(city, other.city) &&
                    JodaBeanUtils.equal(owner, other.owner);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(number);
        hash = hash * 31 + JodaBeanUtils.hashCode(street);
        hash = hash * 31 + JodaBeanUtils.hashCode(city);
        hash = hash * 31 + JodaBeanUtils.hashCode(owner);
        return hash;
    }

    @Override
    public String toString() {
        String buf = "ImmMinimalPrivateBuilder{" +
                "number" + '=' + JodaBeanUtils.toString(number) + ',' + ' ' +
                "street" + '=' + JodaBeanUtils.toString(street) + ',' + ' ' +
                "city" + '=' + JodaBeanUtils.toString(city) + ',' + ' ' +
                "owner" + '=' + JodaBeanUtils.toString(owner) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The bean-builder for {@code ImmMinimalPrivateBuilder}.
     */
    private static final class Builder extends DirectPrivateBeanBuilder<ImmMinimalPrivateBuilder> {

        private int number;
        private String street;
        private String city;
        private ImmPerson owner;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1034364087:  // number
                    return number;
                case -891990013:  // street
                    return street;
                case 3053931:  // city
                    return city;
                case 106164915:  // owner
                    return owner;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case -1034364087:  // number
                    this.number = (Integer) newValue;
                    break;
                case -891990013:  // street
                    this.street = (String) newValue;
                    break;
                case 3053931:  // city
                    this.city = (String) newValue;
                    break;
                case 106164915:  // owner
                    this.owner = (ImmPerson) newValue;
                    break;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
            return this;
        }

        @Override
        public ImmMinimalPrivateBuilder build() {
            return new ImmMinimalPrivateBuilder(
                    number,
                    street,
                    city,
                    owner);
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            String buf = "ImmMinimalPrivateBuilder.Builder{" +
                    "number" + '=' + JodaBeanUtils.toString(number) + ',' + ' ' +
                    "street" + '=' + JodaBeanUtils.toString(street) + ',' + ' ' +
                    "city" + '=' + JodaBeanUtils.toString(city) + ',' + ' ' +
                    "owner" + '=' + JodaBeanUtils.toString(owner) +
                    '}';
            return buf;
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
