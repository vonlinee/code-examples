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
package io.devpl.sdk.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import io.devpl.sdk.beans.sample.FinalFieldBean;
import io.devpl.sdk.beans.sample.Person;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test FinalFieldBean.
 */
public class TestFinalFieldBean {

    /** Bean. */
    private FinalFieldBean bean;

    @Before
    public void setUp() {
        bean = new FinalFieldBean("Hello");
        bean.setFieldNonFinal("Hello");
        bean.getListFinal().add("Hello");
        bean.getFlexiFinal().append("Hello", "World");
        bean.getPersonFinal().setSurname("Hello");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_finalString() {
        assertEquals(bean.getFieldFinal(), "Hello");
        Assert.assertEquals(bean.fieldFinal().get(), "Hello");
        Assert.assertEquals(bean.fieldFinal().metaProperty().declaringType(), FinalFieldBean.class);
        Assert.assertEquals(bean.fieldFinal().metaProperty().getString(bean), "Hello");
        Assert.assertEquals(bean.fieldFinal().metaProperty().get(bean), "Hello");
        Assert.assertEquals(bean.fieldFinal().metaProperty().name(), "fieldFinal");
        Assert.assertEquals(bean.fieldFinal().metaProperty().style(), PropertyStyle.READ_ONLY);
        Assert.assertEquals(bean.fieldFinal().metaProperty().propertyType(), String.class);
        try {
            bean.fieldFinal().set("foo");
            fail();
        } catch (UnsupportedOperationException ex) {
            // expected
        }
        try {
            bean.fieldFinal().metaProperty().set(bean, "foo");
            fail();
        } catch (UnsupportedOperationException ex) {
            // expected
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_nonFinalString() {
        assertEquals(bean.getFieldNonFinal(), "Hello");
        Assert.assertEquals(bean.fieldNonFinal().get(), "Hello");
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().declaringType(), FinalFieldBean.class);
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().getString(bean), "Hello");
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().get(bean), "Hello");
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().name(), "fieldNonFinal");
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().style(), PropertyStyle.READ_WRITE);
        Assert.assertEquals(bean.fieldNonFinal().metaProperty().propertyType(), String.class);
        
        bean.fieldNonFinal().set("foo");
        assertEquals(bean.getFieldNonFinal(), "foo");
        
        bean.fieldNonFinal().metaProperty().set(bean, "bar");
        assertEquals(bean.getFieldNonFinal(), "bar");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_finalList() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        assertEquals(bean.getListFinal(), list);
        Assert.assertEquals(bean.listFinal().get(), list);
        Assert.assertEquals(bean.listFinal().metaProperty().declaringType(), FinalFieldBean.class);
        Assert.assertEquals(bean.listFinal().metaProperty().get(bean), list);
        Assert.assertEquals(bean.listFinal().metaProperty().name(), "listFinal");
        Assert.assertEquals(bean.listFinal().metaProperty().style(), PropertyStyle.READ_WRITE);
        Assert.assertEquals(bean.listFinal().metaProperty().propertyType(), List.class);
        
        list.add("foo");
        List<String> expected1 = new ArrayList<>(list);
        bean.listFinal().set(list);
        assertEquals(bean.getListFinal(), expected1);
        
        list.add("bar");
        List<String> expected2 = new ArrayList<>(list);
        bean.listFinal().metaProperty().set(bean, list);
        assertEquals(bean.getListFinal(), expected2);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_finalBean() {
        FlexiBean flexi = new FlexiBean();
        flexi.append("Hello", "World");
        assertEquals(bean.getFlexiFinal(), flexi);
        assertEquals(bean.flexiFinal().get(), flexi);
        Assert.assertEquals(bean.flexiFinal().metaProperty().declaringType(), FinalFieldBean.class);
        assertEquals(bean.flexiFinal().metaProperty().get(bean), flexi);
        Assert.assertEquals(bean.flexiFinal().metaProperty().name(), "flexiFinal");
        Assert.assertEquals(bean.flexiFinal().metaProperty().style(), PropertyStyle.READ_WRITE);
        Assert.assertEquals(bean.flexiFinal().metaProperty().propertyType(), FlexiBean.class);
        
        flexi.append("foo", "foos");
        FlexiBean expected1 = new FlexiBean(flexi);
        bean.flexiFinal().set(flexi);
        assertEquals(bean.getFlexiFinal(), expected1);
        
        flexi.append("bar", "bars");
        FlexiBean expected2 = new FlexiBean(flexi);
        bean.flexiFinal().metaProperty().set(bean, flexi);
        assertEquals(bean.getFlexiFinal(), expected2);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_finalPerson() {
        Person person = new Person();
        person.setSurname("Hello");
        assertEquals(bean.getPersonFinal(), person);
        Assert.assertEquals(bean.personFinal().get(), person);
        Assert.assertEquals(bean.personFinal().metaProperty().declaringType(), FinalFieldBean.class);
        Assert.assertEquals(bean.personFinal().metaProperty().get(bean), person);
        Assert.assertEquals(bean.personFinal().metaProperty().name(), "personFinal");
        Assert.assertEquals(bean.personFinal().metaProperty().style(), PropertyStyle.READ_ONLY);
        Assert.assertEquals(bean.personFinal().metaProperty().propertyType(), Person.class);
        try {
            bean.personFinal().set(new Person());
            fail();
        } catch (UnsupportedOperationException ex) {
            // expected
        }
        try {
            bean.personFinal().metaProperty().set(bean, new Person());
            fail();
        } catch (UnsupportedOperationException ex) {
            // expected
        }
    }

}
