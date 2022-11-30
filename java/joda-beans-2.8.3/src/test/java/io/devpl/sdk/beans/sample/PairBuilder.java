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

import io.devpl.sdk.beans.JodaBeanUtils;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.impl.StandardImmutableBeanBuilder;

/**
 * Mock builder, used for testing.
 */
public final class PairBuilder extends StandardImmutableBeanBuilder<PairManualTopLevelBuilder> {

    private String first;
    private String second;

    PairBuilder() {
        super(PairManualTopLevelBuilder.meta());
    }

    PairBuilder(PairManualTopLevelBuilder beanToCopy) {
        super(PairManualTopLevelBuilder.meta());
        this.first = beanToCopy.getFirst();
        this.second = beanToCopy.getSecond();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        switch (propertyName) {
            case "first":
                return first;
            case "second":
                return second;
            default:
                throw new NoSuchElementException("Unknown property: " + propertyName);
        }
    }

    @Override
    public PairBuilder set(String propertyName, Object newValue) {
        switch (propertyName) {
            case "first":
                this.first = (String) newValue;
                break;
            case "second":
                this.second = (String) newValue;
                break;
            default:
                throw new NoSuchElementException("Unknown property: " + propertyName);
        }
        return this;
    }

    @Override
    public PairBuilder set(MetaProperty<?> property, Object value) {
        super.set(property, value);
        return this;
    }

    @Override
    public PairManualTopLevelBuilder build() {
        return new PairManualTopLevelBuilder(first, second);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the first value.
     * @param first  the new value
     * @return this, for chaining, not null
     */
    public PairBuilder first(String first) {
        this.first = first;
        return this;
    }

    /**
     * Sets the second value.
     * @param second  the new value
     * @return this, for chaining, not null
     */
    public PairBuilder second(String second) {
        this.second = second;
        return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        String buf = "PairManualBuilder.Builder{" +
                "first" + '=' + JodaBeanUtils.toString(first) + ',' + ' ' +
                "second" + '=' + JodaBeanUtils.toString(second) +
                '}';
        return buf;
    }

}
