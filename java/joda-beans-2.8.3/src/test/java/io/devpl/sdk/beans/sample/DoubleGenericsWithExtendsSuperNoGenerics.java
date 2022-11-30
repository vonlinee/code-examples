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
import java.util.List;
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
import io.devpl.sdk.beans.impl.direct.DirectMetaProperty;
import io.devpl.sdk.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class DoubleGenericsWithExtendsSuperNoGenerics<T extends Serializable, U extends Number>
        extends Company {

    /** The normal type. */
    @PropertyDefinition
    private String normalType;
    /** The type T value. */
    @PropertyDefinition
    private T typeT;
    /** The type U value. */
    @PropertyDefinition
    private U typeU;
    /** The type T value. */
    @PropertyDefinition
    private List<T> typeTList;
    /** The type U value. */
    @PropertyDefinition
    private List<U> typeUList;
    /** The type T value. */
    @PropertyDefinition
    private T[] typeTArray;
    /** The type U value. */
    @PropertyDefinition
    private U[] typeUArray;

    /**
     * Creates an instance.
     */
    public DoubleGenericsWithExtendsSuperNoGenerics() {
    }

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code DoubleGenericsWithExtendsSuperNoGenerics}.
     * @return the meta-bean, not null
     */
    @SuppressWarnings("rawtypes")
    public static DoubleGenericsWithExtendsSuperNoGenerics.Meta meta() {
        return DoubleGenericsWithExtendsSuperNoGenerics.Meta.INSTANCE;
    }

    /**
     * The meta-bean for {@code DoubleGenericsWithExtendsSuperNoGenerics}.
     * @param <R>  the first generic type
     * @param <S>  the second generic type
     * @param cls1  the first generic type
     * @param cls2  the second generic type
     * @return the meta-bean, not null
     */
    @SuppressWarnings("unchecked")
    public static <R extends Serializable, S extends Number> DoubleGenericsWithExtendsSuperNoGenerics.Meta<R, S> metaDoubleGenericsWithExtendsSuperNoGenerics(Class<R> cls1, Class<S> cls2) {
        return DoubleGenericsWithExtendsSuperNoGenerics.Meta.INSTANCE;
    }

    static {
        MetaBean.register(DoubleGenericsWithExtendsSuperNoGenerics.Meta.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DoubleGenericsWithExtendsSuperNoGenerics.Meta<T, U> metaBean() {
        return DoubleGenericsWithExtendsSuperNoGenerics.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the normal type.
     * @return the value of the property
     */
    public String getNormalType() {
        return normalType;
    }

    /**
     * Sets the normal type.
     * @param normalType  the new value of the property
     */
    public void setNormalType(String normalType) {
        this.normalType = normalType;
    }

    /**
     * Gets the the {@code normalType} property.
     * @return the property, not null
     */
    public final Property<String> normalType() {
        return metaBean().normalType().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type T value.
     * @return the value of the property
     */
    public T getTypeT() {
        return typeT;
    }

    /**
     * Sets the type T value.
     * @param typeT  the new value of the property
     */
    public void setTypeT(T typeT) {
        this.typeT = typeT;
    }

    /**
     * Gets the the {@code typeT} property.
     * @return the property, not null
     */
    public final Property<T> typeT() {
        return metaBean().typeT().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type U value.
     * @return the value of the property
     */
    public U getTypeU() {
        return typeU;
    }

    /**
     * Sets the type U value.
     * @param typeU  the new value of the property
     */
    public void setTypeU(U typeU) {
        this.typeU = typeU;
    }

    /**
     * Gets the the {@code typeU} property.
     * @return the property, not null
     */
    public final Property<U> typeU() {
        return metaBean().typeU().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type T value.
     * @return the value of the property
     */
    public List<T> getTypeTList() {
        return typeTList;
    }

    /**
     * Sets the type T value.
     * @param typeTList  the new value of the property
     */
    public void setTypeTList(List<T> typeTList) {
        this.typeTList = typeTList;
    }

    /**
     * Gets the the {@code typeTList} property.
     * @return the property, not null
     */
    public final Property<List<T>> typeTList() {
        return metaBean().typeTList().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type U value.
     * @return the value of the property
     */
    public List<U> getTypeUList() {
        return typeUList;
    }

    /**
     * Sets the type U value.
     * @param typeUList  the new value of the property
     */
    public void setTypeUList(List<U> typeUList) {
        this.typeUList = typeUList;
    }

    /**
     * Gets the the {@code typeUList} property.
     * @return the property, not null
     */
    public final Property<List<U>> typeUList() {
        return metaBean().typeUList().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type T value.
     * @return the value of the property
     */
    public T[] getTypeTArray() {
        return typeTArray;
    }

    /**
     * Sets the type T value.
     * @param typeTArray  the new value of the property
     */
    public void setTypeTArray(T[] typeTArray) {
        this.typeTArray = typeTArray;
    }

    /**
     * Gets the the {@code typeTArray} property.
     * @return the property, not null
     */
    public final Property<T[]> typeTArray() {
        return metaBean().typeTArray().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the type U value.
     * @return the value of the property
     */
    public U[] getTypeUArray() {
        return typeUArray;
    }

    /**
     * Sets the type U value.
     * @param typeUArray  the new value of the property
     */
    public void setTypeUArray(U[] typeUArray) {
        this.typeUArray = typeUArray;
    }

    /**
     * Gets the the {@code typeUArray} property.
     * @return the property, not null
     */
    public final Property<U[]> typeUArray() {
        return metaBean().typeUArray().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public DoubleGenericsWithExtendsSuperNoGenerics<T, U> clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            DoubleGenericsWithExtendsSuperNoGenerics<?, ?> other = (DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) obj;
            return JodaBeanUtils.equal(getNormalType(), other.getNormalType()) &&
                    JodaBeanUtils.equal(getTypeT(), other.getTypeT()) &&
                    JodaBeanUtils.equal(getTypeU(), other.getTypeU()) &&
                    JodaBeanUtils.equal(getTypeTList(), other.getTypeTList()) &&
                    JodaBeanUtils.equal(getTypeUList(), other.getTypeUList()) &&
                    JodaBeanUtils.equal(getTypeTArray(), other.getTypeTArray()) &&
                    JodaBeanUtils.equal(getTypeUArray(), other.getTypeUArray()) &&
                    super.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + JodaBeanUtils.hashCode(getNormalType());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeT());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeU());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeTList());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeUList());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeTArray());
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypeUArray());
        return hash ^ super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(256);
        buf.append("DoubleGenericsWithExtendsSuperNoGenerics{");
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
        buf.append("normalType").append('=').append(JodaBeanUtils.toString(getNormalType())).append(',').append(' ');
        buf.append("typeT").append('=').append(JodaBeanUtils.toString(getTypeT())).append(',').append(' ');
        buf.append("typeU").append('=').append(JodaBeanUtils.toString(getTypeU())).append(',').append(' ');
        buf.append("typeTList").append('=').append(JodaBeanUtils.toString(getTypeTList())).append(',').append(' ');
        buf.append("typeUList").append('=').append(JodaBeanUtils.toString(getTypeUList())).append(',').append(' ');
        buf.append("typeTArray").append('=').append(JodaBeanUtils.toString(getTypeTArray())).append(',').append(' ');
        buf.append("typeUArray").append('=').append(JodaBeanUtils.toString(getTypeUArray())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code DoubleGenericsWithExtendsSuperNoGenerics}.
     * @param <T>  the type
     * @param <U>  the type
     */
    public static class Meta<T extends Serializable, U extends Number> extends Company.Meta {
        /**
         * The singleton instance of the meta-bean.
         */
        @SuppressWarnings("rawtypes")
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code normalType} property.
         */
        private final MetaProperty<String> normalType = DirectMetaProperty.ofReadWrite(
                this, "normalType", DoubleGenericsWithExtendsSuperNoGenerics.class, String.class);
        /**
         * The meta-property for the {@code typeT} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<T> typeT = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
                this, "typeT", DoubleGenericsWithExtendsSuperNoGenerics.class, Object.class);
        /**
         * The meta-property for the {@code typeU} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<U> typeU = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
                this, "typeU", DoubleGenericsWithExtendsSuperNoGenerics.class, Object.class);
        /**
         * The meta-property for the {@code typeTList} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<T>> typeTList = DirectMetaProperty.ofReadWrite(
                this, "typeTList", DoubleGenericsWithExtendsSuperNoGenerics.class, (Class) List.class);
        /**
         * The meta-property for the {@code typeUList} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<U>> typeUList = DirectMetaProperty.ofReadWrite(
                this, "typeUList", DoubleGenericsWithExtendsSuperNoGenerics.class, (Class) List.class);
        /**
         * The meta-property for the {@code typeTArray} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<T[]> typeTArray = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
                this, "typeTArray", DoubleGenericsWithExtendsSuperNoGenerics.class, Object[].class);
        /**
         * The meta-property for the {@code typeUArray} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<U[]> typeUArray = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
                this, "typeUArray", DoubleGenericsWithExtendsSuperNoGenerics.class, Object[].class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, (DirectMetaPropertyMap) super.metaPropertyMap(),
                "normalType",
                "typeT",
                "typeU",
                "typeTList",
                "typeUList",
                "typeTArray",
                "typeUArray");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1255672639:  // normalType
                    return normalType;
                case 110843994:  // typeT
                    return typeT;
                case 110843995:  // typeU
                    return typeU;
                case 508018712:  // typeTList
                    return typeTList;
                case 508942233:  // typeUList
                    return typeUList;
                case -1441181153:  // typeTArray
                    return typeTArray;
                case -1412552002:  // typeUArray
                    return typeUArray;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends DoubleGenericsWithExtendsSuperNoGenerics<T, U>> builder() {
            return new DirectBeanBuilder<>(new DoubleGenericsWithExtendsSuperNoGenerics<T, U>());
        }

        @SuppressWarnings({"unchecked", "rawtypes" })
        @Override
        public Class<? extends DoubleGenericsWithExtendsSuperNoGenerics<T, U>> beanType() {
            return (Class) DoubleGenericsWithExtendsSuperNoGenerics.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code normalType} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> normalType() {
            return normalType;
        }

        /**
         * The meta-property for the {@code typeT} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<T> typeT() {
            return typeT;
        }

        /**
         * The meta-property for the {@code typeU} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<U> typeU() {
            return typeU;
        }

        /**
         * The meta-property for the {@code typeTList} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<T>> typeTList() {
            return typeTList;
        }

        /**
         * The meta-property for the {@code typeUList} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<U>> typeUList() {
            return typeUList;
        }

        /**
         * The meta-property for the {@code typeTArray} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<T[]> typeTArray() {
            return typeTArray;
        }

        /**
         * The meta-property for the {@code typeUArray} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<U[]> typeUArray() {
            return typeUArray;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1255672639:  // normalType
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getNormalType();
                case 110843994:  // typeT
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeT();
                case 110843995:  // typeU
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeU();
                case 508018712:  // typeTList
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeTList();
                case 508942233:  // typeUList
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeUList();
                case -1441181153:  // typeTArray
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeTArray();
                case -1412552002:  // typeUArray
                    return ((DoubleGenericsWithExtendsSuperNoGenerics<?, ?>) bean).getTypeUArray();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1255672639:  // normalType
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setNormalType((String) newValue);
                    return;
                case 110843994:  // typeT
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeT((T) newValue);
                    return;
                case 110843995:  // typeU
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeU((U) newValue);
                    return;
                case 508018712:  // typeTList
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeTList((List<T>) newValue);
                    return;
                case 508942233:  // typeUList
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeUList((List<U>) newValue);
                    return;
                case -1441181153:  // typeTArray
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeTArray((T[]) newValue);
                    return;
                case -1412552002:  // typeUArray
                    ((DoubleGenericsWithExtendsSuperNoGenerics<T, U>) bean).setTypeUArray((U[]) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
