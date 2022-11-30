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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.direct.DirectBeanMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectFieldsBeanBuilder;
import io.devpl.sdk.beans.impl.direct.DirectMetaBean;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Mock person JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition(builderScope = "public")
public final class SimplePersonWithBuilderFinal implements Cloneable, Bean {

    /** The forename. */
    @PropertyDefinition
    private String forename;
    /** The surname. */
    @PropertyDefinition(validate = "notNull")
    private final String surname;
    /** The number of cars. */
    @PropertyDefinition
    private transient int numberOfCars;
    @PropertyDefinition
    private final List<Address> addressList = new ArrayList<>();
    @PropertyDefinition(validate = "notNull")
    private final Map<String, Address> otherAddressMap = new HashMap<>();
    @PropertyDefinition(validate = "notNull")
    private final List<List<Address>> addressesList = new ArrayList<>();
    @PropertyDefinition
    private Address mainAddress;
    @PropertyDefinition
    private String[] tags;

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code SimplePersonWithBuilderFinal}.
     * @return the meta-bean, not null
     */
    public static SimplePersonWithBuilderFinal.Meta meta() {
        return SimplePersonWithBuilderFinal.Meta.INSTANCE;
    }

    static {
        MetaBean.register(SimplePersonWithBuilderFinal.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static SimplePersonWithBuilderFinal.Builder builder() {
        return new SimplePersonWithBuilderFinal.Builder();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    private SimplePersonWithBuilderFinal(SimplePersonWithBuilderFinal.Builder builder) {
        JodaBeanUtils.notNull(builder.surname, "surname");
        JodaBeanUtils.notNull(builder.addressList, "addressList");
        JodaBeanUtils.notNull(builder.otherAddressMap, "otherAddressMap");
        JodaBeanUtils.notNull(builder.addressesList, "addressesList");
        this.forename = builder.forename;
        this.surname = builder.surname;
        this.numberOfCars = builder.numberOfCars;
        this.addressList.addAll(builder.addressList);
        this.otherAddressMap.putAll(builder.otherAddressMap);
        this.addressesList.addAll(builder.addressesList);
        this.mainAddress = builder.mainAddress;
        this.tags = builder.tags;
    }

    @Override
    public SimplePersonWithBuilderFinal.Meta metaBean() {
        return SimplePersonWithBuilderFinal.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the forename.
     * @return the value of the property
     */
    public String getForename() {
        return forename;
    }

    /**
     * Sets the forename.
     * @param forename  the new value of the property
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Gets the the {@code forename} property.
     * @return the property, not null
     */
    public Property<String> forename() {
        return metaBean().forename().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the surname.
     * @return the value of the property, not null
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the the {@code surname} property.
     * @return the property, not null
     */
    public Property<String> surname() {
        return metaBean().surname().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number of cars.
     * @return the value of the property
     */
    public int getNumberOfCars() {
        return numberOfCars;
    }

    /**
     * Sets the number of cars.
     * @param numberOfCars  the new value of the property
     */
    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    /**
     * Gets the the {@code numberOfCars} property.
     * @return the property, not null
     */
    public Property<Integer> numberOfCars() {
        return metaBean().numberOfCars().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the addressList.
     * @return the value of the property, not null
     */
    public List<Address> getAddressList() {
        return addressList;
    }

    /**
     * Sets the addressList.
     * @param addressList  the new value of the property, not null
     */
    public void setAddressList(List<Address> addressList) {
        JodaBeanUtils.notNull(addressList, "addressList");
        this.addressList.clear();
        this.addressList.addAll(addressList);
    }

    /**
     * Gets the the {@code addressList} property.
     * @return the property, not null
     */
    public Property<List<Address>> addressList() {
        return metaBean().addressList().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the otherAddressMap.
     * @return the value of the property, not null
     */
    public Map<String, Address> getOtherAddressMap() {
        return otherAddressMap;
    }

    /**
     * Sets the otherAddressMap.
     * @param otherAddressMap  the new value of the property, not null
     */
    public void setOtherAddressMap(Map<String, Address> otherAddressMap) {
        JodaBeanUtils.notNull(otherAddressMap, "otherAddressMap");
        this.otherAddressMap.clear();
        this.otherAddressMap.putAll(otherAddressMap);
    }

    /**
     * Gets the the {@code otherAddressMap} property.
     * @return the property, not null
     */
    public Property<Map<String, Address>> otherAddressMap() {
        return metaBean().otherAddressMap().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the addressesList.
     * @return the value of the property, not null
     */
    public List<List<Address>> getAddressesList() {
        return addressesList;
    }

    /**
     * Sets the addressesList.
     * @param addressesList  the new value of the property, not null
     */
    public void setAddressesList(List<List<Address>> addressesList) {
        JodaBeanUtils.notNull(addressesList, "addressesList");
        this.addressesList.clear();
        this.addressesList.addAll(addressesList);
    }

    /**
     * Gets the the {@code addressesList} property.
     * @return the property, not null
     */
    public Property<List<List<Address>>> addressesList() {
        return metaBean().addressesList().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the mainAddress.
     * @return the value of the property
     */
    public Address getMainAddress() {
        return mainAddress;
    }

    /**
     * Sets the mainAddress.
     * @param mainAddress  the new value of the property
     */
    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    /**
     * Gets the the {@code mainAddress} property.
     * @return the property, not null
     */
    public Property<Address> mainAddress() {
        return metaBean().mainAddress().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the tags.
     * @return the value of the property
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Sets the tags.
     * @param tags  the new value of the property
     */
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * Gets the the {@code tags} property.
     * @return the property, not null
     */
    public Property<String[]> tags() {
        return metaBean().tags().createProperty(this);
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
    public SimplePersonWithBuilderFinal clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            SimplePersonWithBuilderFinal other = (SimplePersonWithBuilderFinal) obj;
            return JodaBeanUtils.equal(getForename(), other.getForename()) &&
                    JodaBeanUtils.equal(getSurname(), other.getSurname()) &&
                    (getNumberOfCars() == other.getNumberOfCars()) &&
                    JodaBeanUtils.equal(getAddressList(), other.getAddressList()) &&
                    JodaBeanUtils.equal(getOtherAddressMap(), other.getOtherAddressMap()) &&
                    JodaBeanUtils.equal(getAddressesList(), other.getAddressesList()) &&
                    JodaBeanUtils.equal(getMainAddress(), other.getMainAddress()) &&
                    JodaBeanUtils.equal(getTags(), other.getTags());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getForename());
        hash = hash * 31 + JodaBeanUtils.hashCode(getSurname());
        hash = hash * 31 + JodaBeanUtils.hashCode(getNumberOfCars());
        hash = hash * 31 + JodaBeanUtils.hashCode(getAddressList());
        hash = hash * 31 + JodaBeanUtils.hashCode(getOtherAddressMap());
        hash = hash * 31 + JodaBeanUtils.hashCode(getAddressesList());
        hash = hash * 31 + JodaBeanUtils.hashCode(getMainAddress());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTags());
        return hash;
    }

    @Override
    public String toString() {
        String buf = "SimplePersonWithBuilderFinal{" +
                "forename" + '=' + JodaBeanUtils.toString(getForename()) + ',' + ' ' +
                "surname" + '=' + JodaBeanUtils.toString(getSurname()) + ',' + ' ' +
                "numberOfCars" + '=' + JodaBeanUtils.toString(getNumberOfCars()) + ',' + ' ' +
                "addressList" + '=' + JodaBeanUtils.toString(getAddressList()) + ',' + ' ' +
                "otherAddressMap" + '=' + JodaBeanUtils.toString(getOtherAddressMap()) + ',' + ' ' +
                "addressesList" + '=' + JodaBeanUtils.toString(getAddressesList()) + ',' + ' ' +
                "mainAddress" + '=' + JodaBeanUtils.toString(getMainAddress()) + ',' + ' ' +
                "tags" + '=' + JodaBeanUtils.toString(getTags()) +
                '}';
        return buf;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code SimplePersonWithBuilderFinal}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code forename} property.
         */
        private final MetaProperty<String> forename = DirectBeanMetaProperty.ofReadWrite(
                this, "forename", SimplePersonWithBuilderFinal.class, String.class);
        /**
         * The meta-property for the {@code surname} property.
         */
        private final MetaProperty<String> surname = DirectBeanMetaProperty.ofReadOnlyBuildable(
                this, "surname", SimplePersonWithBuilderFinal.class, String.class);
        /**
         * The meta-property for the {@code numberOfCars} property.
         */
        private final MetaProperty<Integer> numberOfCars = DirectBeanMetaProperty.ofReadWrite(
                this, "numberOfCars", SimplePersonWithBuilderFinal.class, Integer.TYPE);
        /**
         * The meta-property for the {@code addressList} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<Address>> addressList = DirectBeanMetaProperty.ofReadWrite(
                this, "addressList", SimplePersonWithBuilderFinal.class, (Class) List.class);
        /**
         * The meta-property for the {@code otherAddressMap} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<Map<String, Address>> otherAddressMap = DirectBeanMetaProperty.ofReadWrite(
                this, "otherAddressMap", SimplePersonWithBuilderFinal.class, (Class) Map.class);
        /**
         * The meta-property for the {@code addressesList} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<List<Address>>> addressesList = DirectBeanMetaProperty.ofReadWrite(
                this, "addressesList", SimplePersonWithBuilderFinal.class, (Class) List.class);
        /**
         * The meta-property for the {@code mainAddress} property.
         */
        private final MetaProperty<Address> mainAddress = DirectBeanMetaProperty.ofReadWrite(
                this, "mainAddress", SimplePersonWithBuilderFinal.class, Address.class);
        /**
         * The meta-property for the {@code tags} property.
         */
        private final MetaProperty<String[]> tags = DirectBeanMetaProperty.ofReadWrite(
                this, "tags", SimplePersonWithBuilderFinal.class, String[].class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "forename",
                "surname",
                "numberOfCars",
                "addressList",
                "otherAddressMap",
                "addressesList",
                "mainAddress",
                "tags");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 467061063:  // forename
                    return forename;
                case -1852993317:  // surname
                    return surname;
                case 926656063:  // numberOfCars
                    return numberOfCars;
                case -1377524046:  // addressList
                    return addressList;
                case 1368089592:  // otherAddressMap
                    return otherAddressMap;
                case -226885792:  // addressesList
                    return addressesList;
                case -2032731141:  // mainAddress
                    return mainAddress;
                case 3552281:  // tags
                    return tags;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public SimplePersonWithBuilderFinal.Builder builder() {
            return new SimplePersonWithBuilderFinal.Builder();
        }

        @Override
        public Class<? extends SimplePersonWithBuilderFinal> beanType() {
            return SimplePersonWithBuilderFinal.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code forename} property.
         * @return the meta-property, not null
         */
        public MetaProperty<String> forename() {
            return forename;
        }

        /**
         * The meta-property for the {@code surname} property.
         * @return the meta-property, not null
         */
        public MetaProperty<String> surname() {
            return surname;
        }

        /**
         * The meta-property for the {@code numberOfCars} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Integer> numberOfCars() {
            return numberOfCars;
        }

        /**
         * The meta-property for the {@code addressList} property.
         * @return the meta-property, not null
         */
        public MetaProperty<List<Address>> addressList() {
            return addressList;
        }

        /**
         * The meta-property for the {@code otherAddressMap} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Map<String, Address>> otherAddressMap() {
            return otherAddressMap;
        }

        /**
         * The meta-property for the {@code addressesList} property.
         * @return the meta-property, not null
         */
        public MetaProperty<List<List<Address>>> addressesList() {
            return addressesList;
        }

        /**
         * The meta-property for the {@code mainAddress} property.
         * @return the meta-property, not null
         */
        public MetaProperty<Address> mainAddress() {
            return mainAddress;
        }

        /**
         * The meta-property for the {@code tags} property.
         * @return the meta-property, not null
         */
        public MetaProperty<String[]> tags() {
            return tags;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 467061063:  // forename
                    return ((SimplePersonWithBuilderFinal) bean).getForename();
                case -1852993317:  // surname
                    return ((SimplePersonWithBuilderFinal) bean).getSurname();
                case 926656063:  // numberOfCars
                    return ((SimplePersonWithBuilderFinal) bean).getNumberOfCars();
                case -1377524046:  // addressList
                    return ((SimplePersonWithBuilderFinal) bean).getAddressList();
                case 1368089592:  // otherAddressMap
                    return ((SimplePersonWithBuilderFinal) bean).getOtherAddressMap();
                case -226885792:  // addressesList
                    return ((SimplePersonWithBuilderFinal) bean).getAddressesList();
                case -2032731141:  // mainAddress
                    return ((SimplePersonWithBuilderFinal) bean).getMainAddress();
                case 3552281:  // tags
                    return ((SimplePersonWithBuilderFinal) bean).getTags();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 467061063:  // forename
                    ((SimplePersonWithBuilderFinal) bean).setForename((String) newValue);
                    return;
                case -1852993317:  // surname
                    if (quiet) {
                        return;
                    }
                    throw new UnsupportedOperationException("Property cannot be written: surname");
                case 926656063:  // numberOfCars
                    ((SimplePersonWithBuilderFinal) bean).setNumberOfCars((Integer) newValue);
                    return;
                case -1377524046:  // addressList
                    ((SimplePersonWithBuilderFinal) bean).setAddressList((List<Address>) newValue);
                    return;
                case 1368089592:  // otherAddressMap
                    ((SimplePersonWithBuilderFinal) bean).setOtherAddressMap((Map<String, Address>) newValue);
                    return;
                case -226885792:  // addressesList
                    ((SimplePersonWithBuilderFinal) bean).setAddressesList((List<List<Address>>) newValue);
                    return;
                case -2032731141:  // mainAddress
                    ((SimplePersonWithBuilderFinal) bean).setMainAddress((Address) newValue);
                    return;
                case 3552281:  // tags
                    ((SimplePersonWithBuilderFinal) bean).setTags((String[]) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notNull(((SimplePersonWithBuilderFinal) bean).surname, "surname");
            JodaBeanUtils.notNull(((SimplePersonWithBuilderFinal) bean).addressList, "addressList");
            JodaBeanUtils.notNull(((SimplePersonWithBuilderFinal) bean).otherAddressMap, "otherAddressMap");
            JodaBeanUtils.notNull(((SimplePersonWithBuilderFinal) bean).addressesList, "addressesList");
        }

    }

    //-----------------------------------------------------------------------
    /**
     * The bean-builder for {@code SimplePersonWithBuilderFinal}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<SimplePersonWithBuilderFinal> {

        private String forename;
        private String surname;
        private int numberOfCars;
        private List<Address> addressList = ImmutableList.of();
        private Map<String, Address> otherAddressMap = ImmutableMap.of();
        private List<List<Address>> addressesList = ImmutableList.of();
        private Address mainAddress;
        private String[] tags;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        private Builder(SimplePersonWithBuilderFinal beanToCopy) {
            this.forename = beanToCopy.getForename();
            this.surname = beanToCopy.getSurname();
            this.numberOfCars = beanToCopy.getNumberOfCars();
            this.addressList = ImmutableList.copyOf(beanToCopy.getAddressList());
            this.otherAddressMap = ImmutableMap.copyOf(beanToCopy.getOtherAddressMap());
            this.addressesList = ImmutableList.copyOf(beanToCopy.getAddressesList());
            this.mainAddress = beanToCopy.getMainAddress();
            this.tags = (beanToCopy.getTags() != null ? beanToCopy.getTags().clone() : null);
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 467061063:  // forename
                    return forename;
                case -1852993317:  // surname
                    return surname;
                case 926656063:  // numberOfCars
                    return numberOfCars;
                case -1377524046:  // addressList
                    return addressList;
                case 1368089592:  // otherAddressMap
                    return otherAddressMap;
                case -226885792:  // addressesList
                    return addressesList;
                case -2032731141:  // mainAddress
                    return mainAddress;
                case 3552281:  // tags
                    return tags;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 467061063:  // forename
                    this.forename = (String) newValue;
                    break;
                case -1852993317:  // surname
                    this.surname = (String) newValue;
                    break;
                case 926656063:  // numberOfCars
                    this.numberOfCars = (Integer) newValue;
                    break;
                case -1377524046:  // addressList
                    this.addressList = (List<Address>) newValue;
                    break;
                case 1368089592:  // otherAddressMap
                    this.otherAddressMap = (Map<String, Address>) newValue;
                    break;
                case -226885792:  // addressesList
                    this.addressesList = (List<List<Address>>) newValue;
                    break;
                case -2032731141:  // mainAddress
                    this.mainAddress = (Address) newValue;
                    break;
                case 3552281:  // tags
                    this.tags = (String[]) newValue;
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
        public SimplePersonWithBuilderFinal build() {
            return new SimplePersonWithBuilderFinal(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the forename.
         * @param forename  the new value
         * @return this, for chaining, not null
         */
        public Builder forename(String forename) {
            this.forename = forename;
            return this;
        }

        /**
         * Sets the surname.
         * @param surname  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder surname(String surname) {
            JodaBeanUtils.notNull(surname, "surname");
            this.surname = surname;
            return this;
        }

        /**
         * Sets the number of cars.
         * @param numberOfCars  the new value
         * @return this, for chaining, not null
         */
        public Builder numberOfCars(int numberOfCars) {
            this.numberOfCars = numberOfCars;
            return this;
        }

        /**
         * Sets the addressList.
         * @param addressList  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder addressList(List<Address> addressList) {
            JodaBeanUtils.notNull(addressList, "addressList");
            this.addressList = addressList;
            return this;
        }

        /**
         * Sets the {@code addressList} property in the builder
         * from an array of objects.
         * @param addressList  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder addressList(Address... addressList) {
            return addressList(ImmutableList.copyOf(addressList));
        }

        /**
         * Sets the otherAddressMap.
         * @param otherAddressMap  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder otherAddressMap(Map<String, Address> otherAddressMap) {
            JodaBeanUtils.notNull(otherAddressMap, "otherAddressMap");
            this.otherAddressMap = otherAddressMap;
            return this;
        }

        /**
         * Sets the addressesList.
         * @param addressesList  the new value, not null
         * @return this, for chaining, not null
         */
        public Builder addressesList(List<List<Address>> addressesList) {
            JodaBeanUtils.notNull(addressesList, "addressesList");
            this.addressesList = addressesList;
            return this;
        }

        /**
         * Sets the {@code addressesList} property in the builder
         * from an array of objects.
         * @param addressesList  the new value, not null
         * @return this, for chaining, not null
         */
        @SafeVarargs
        public final Builder addressesList(List<Address>... addressesList) {
            return addressesList(ImmutableList.copyOf(addressesList));
        }

        /**
         * Sets the mainAddress.
         * @param mainAddress  the new value
         * @return this, for chaining, not null
         */
        public Builder mainAddress(Address mainAddress) {
            this.mainAddress = mainAddress;
            return this;
        }

        /**
         * Sets the tags.
         * @param tags  the new value
         * @return this, for chaining, not null
         */
        public Builder tags(String... tags) {
            this.tags = tags;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(288);
            buf.append("SimplePersonWithBuilderFinal.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        private void toString(StringBuilder buf) {
            buf.append("forename").append('=').append(JodaBeanUtils.toString(forename)).append(',').append(' ');
            buf.append("surname").append('=').append(JodaBeanUtils.toString(surname)).append(',').append(' ');
            buf.append("numberOfCars").append('=').append(JodaBeanUtils.toString(numberOfCars)).append(',').append(' ');
            buf.append("addressList").append('=').append(JodaBeanUtils.toString(addressList)).append(',').append(' ');
            buf.append("otherAddressMap").append('=').append(JodaBeanUtils.toString(otherAddressMap)).append(',').append(' ');
            buf.append("addressesList").append('=').append(JodaBeanUtils.toString(addressesList)).append(',').append(' ');
            buf.append("mainAddress").append('=').append(JodaBeanUtils.toString(mainAddress)).append(',').append(' ');
            buf.append("tags").append('=').append(JodaBeanUtils.toString(tags)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
