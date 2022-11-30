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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.sample.Address;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test property using Person.
 */
public class TestAddress {

    private static final int NUM_PROPERTIES = 4;
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String NUMBER = "number";

    @Test
    public void test_bean() {
        Bean test = new Address();

        assertTrue(test instanceof Address);
        
        assertEquals(test.metaBean(), Address.meta());

        assertTrue(test.propertyNames().contains(STREET));
        assertTrue(test.propertyNames().contains(CITY));
        assertTrue(test.propertyNames().contains(NUMBER));
        assertFalse(test.propertyNames().contains("Rubbish"));
        
        assertEquals(test.getProperty(STREET).name(), STREET);
        assertEquals(test.getProperty(CITY).name(), CITY);
        assertEquals(test.getProperty(NUMBER).name(), NUMBER);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_bean_invalidPropertyName() {
        Bean test = Address.meta().builder().build();
        try {
            test.getProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void test_builder1() {
        BeanBuilder<? extends Address> builder = Address.meta().builder();
        builder.set("street", "Main Street");
        assertEquals(builder.get("street"), "Main Street");
        builder.set("city", "London");
        assertEquals(builder.get("street"), "Main Street");
        assertEquals(builder.get("city"), "London");
        String street = builder.get(Address.meta().street());
        assertEquals(street, "Main Street");
        String city = builder.get(Address.meta().city());
        assertEquals(city, "London");
        
        Address test = builder.build();
        Address expected = new Address();
        expected.setStreet("Main Street");
        expected.setCity("London");
        
        assertEquals(test, expected);
    }

    @Test
    public void test_builder2() {
        BeanBuilder<? extends Address> builder = Address.meta().builder();
        builder.set(Address.meta().street(), "Main Street");
        builder.set(Address.meta().number(), 12);
        
        Address test = builder.build();
        Address expected = new Address();
        expected.setStreet("Main Street");
        expected.setNumber(12);
        
        assertEquals(test, expected);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_builder_getInvalidPropertyName() {
        BeanBuilder<? extends Address> builder = Address.meta().builder();
        try {
            builder.get("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void test_builder_setInvalidPropertyName() {
        BeanBuilder<? extends Address> builder = Address.meta().builder();
        try {
            builder.set("Rubbish", "");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaBean() {
        MetaBean test = Address.meta();
        
        assertEquals(test.beanType(), Address.class);
        
        assertEquals(test.beanName(), Address.class.getName());
        
        assertEquals(test.metaPropertyCount(), NUM_PROPERTIES);

        assertTrue(test.metaPropertyExists(STREET));
        assertTrue(test.metaPropertyExists(CITY));
        assertTrue(test.metaPropertyExists(NUMBER));
        assertFalse(test.metaPropertyExists("Rubbish"));
        
        assertEquals(test.metaProperty(STREET).name(), STREET);
        assertEquals(test.metaProperty(CITY).name(), CITY);
        assertEquals(test.metaProperty(NUMBER).name(), NUMBER);
        
        Map<String, MetaProperty<?>> map = test.metaPropertyMap();
        assertEquals(map.size(), NUM_PROPERTIES);
        assertTrue(map.containsKey(STREET));
        assertTrue(map.containsKey(CITY));
        assertTrue(map.containsKey(NUMBER));
        assertFalse(map.containsKey("NotHere"));
        assertEquals(map.get(STREET), Address.meta().street());
    }

    @Test(expected = NoSuchElementException.class)
    public void test_metaBean_invalidPropertyName() {
        MetaBean test = Address.meta();
        try {
            test.metaProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedPropertyMethod() {
        Address address = new Address();
        Property<String> test = address.street();
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), Address.meta().street());

        assertNull(test.get());
        address.setStreet("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_property_String() {
        Address address = new Address();
        Property<String> test = address.getProperty(STREET);
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), Address.meta().street());

        assertNull(test.get());
        address.setStreet("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedMetaPropertyMethod() {
        Address address = new Address();
        MetaProperty<String> test = Address.meta().street();
        
        assertEquals(test.metaBean().beanType(), Address.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.name(), STREET);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(address));
        address.setStreet("A");
        assertEquals(test.get(address), "A");
        test.set(address, "B");
        assertEquals(test.get(address), "B");
        assertEquals(test.put(address, "C"), "B");
        assertEquals(test.get(address), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_String() {
        Address address = new Address();
        MetaProperty<String> test = Address.meta().metaProperty(STREET);
        
        assertEquals(test.metaBean().beanType(), Address.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.name(), STREET);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(address));
        address.setStreet("A");
        assertEquals(test.get(address), "A");
        test.set(address, "B");
        assertEquals(test.get(address), "B");
        assertEquals(test.put(address, "C"), "B");
        assertEquals(test.get(address), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_types() {
        MetaProperty<String> test = Address.meta().street();
        
        assertEquals(test.metaBean().beanType(), Address.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.propertyGenericType(), String.class);
    }

    @Test
    public void test_metaProperty_annotations() {
        MetaProperty<String> prop = Address.meta().street();
        List<Annotation> test = prop.annotations();
        
        assertEquals(test.size(), 1);
        assertTrue(test.get(0) instanceof PropertyDefinition);
    }

}
