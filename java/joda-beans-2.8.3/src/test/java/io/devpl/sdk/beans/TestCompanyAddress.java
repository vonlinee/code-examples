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
import io.devpl.sdk.beans.sample.CompanyAddress;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test property using Person.
 */
public class TestCompanyAddress {

    private static final int NUM_PROPERTIES = 5;
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String NUMBER = "number";
    private static final String COMPANY_NAME = "companyName";

    @Test
    public void test_bean() {
        Bean test = CompanyAddress.meta().builder().build();

        assertTrue(test instanceof CompanyAddress);
        
        assertEquals(test.metaBean(), CompanyAddress.meta());

        assertTrue(test.propertyNames().contains(STREET));
        assertTrue(test.propertyNames().contains(CITY));
        assertTrue(test.propertyNames().contains(NUMBER));
        assertTrue(test.propertyNames().contains(COMPANY_NAME));
        assertFalse(test.propertyNames().contains("Rubbish"));
        
        assertEquals(test.getProperty(STREET).name(), STREET);
        assertEquals(test.getProperty(CITY).name(), CITY);
        assertEquals(test.getProperty(NUMBER).name(), NUMBER);
        assertEquals(test.getProperty(COMPANY_NAME).name(), COMPANY_NAME);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_bean_invalidPropertyName() {
        Bean test = CompanyAddress.meta().builder().build();
        try {
            test.getProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaBean() {
        MetaBean test = CompanyAddress.meta();
        
        assertEquals(test.beanType(), CompanyAddress.class);
        
        assertEquals(test.beanName(), CompanyAddress.class.getName());
        
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
    }

    @Test(expected = NoSuchElementException.class)
    public void test_metaBean_invalidPropertyName() {
        MetaBean test = CompanyAddress.meta();
        try {
            test.metaProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedPropertyMethod_street() {
        CompanyAddress address = new CompanyAddress();
        Property<String> test = address.street();
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), CompanyAddress.meta().street());

        assertNull(test.get());
        address.setStreet("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    @Test
    public void test_namedPropertyMethod_companyName() {
        CompanyAddress address = new CompanyAddress();
        Property<String> test = address.companyName();
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), CompanyAddress.meta().companyName());

        assertNull(test.get());
        address.setCompanyName("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_property_String_street() {
        CompanyAddress address = new CompanyAddress();
        Property<String> test = address.getProperty(STREET);
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), CompanyAddress.meta().street());

        assertNull(test.get());
        address.setStreet("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    @Test
    public void test_property_String_companyName() {
        CompanyAddress address = new CompanyAddress();
        Property<String> test = address.getProperty(COMPANY_NAME);
        
        assertSame(test.bean(), address);
        assertSame(test.metaProperty(), CompanyAddress.meta().companyName());

        assertNull(test.get());
        address.setCompanyName("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedMetaPropertyMethod_street() {
        CompanyAddress address = new CompanyAddress();
        MetaProperty<String> test = CompanyAddress.meta().street();
        
        assertEquals(test.metaBean().beanType(), CompanyAddress.class);
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

    @Test
    public void test_namedMetaPropertyMethod_companyName() {
        CompanyAddress address = new CompanyAddress();
        MetaProperty<String> test = CompanyAddress.meta().companyName();
        
        assertEquals(test.metaBean().beanType(), CompanyAddress.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.name(), COMPANY_NAME);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(address));
        address.setCompanyName("A");
        assertEquals(test.get(address), "A");
        test.set(address, "B");
        assertEquals(test.get(address), "B");
        assertEquals(test.put(address, "C"), "B");
        assertEquals(test.get(address), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_String_street() {
        CompanyAddress address = new CompanyAddress();
        MetaProperty<String> test = CompanyAddress.meta().metaProperty(STREET);
        
        assertEquals(test.metaBean().beanType(), CompanyAddress.class);
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

    @Test
    public void test_metaProperty_String_companyName() {
        CompanyAddress address = new CompanyAddress();
        MetaProperty<String> test = CompanyAddress.meta().metaProperty(COMPANY_NAME);
        
        assertEquals(test.metaBean().beanType(), CompanyAddress.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.name(), COMPANY_NAME);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(address));
        address.setCompanyName("A");
        assertEquals(test.get(address), "A");
        test.set(address, "B");
        assertEquals(test.get(address), "B");
        assertEquals(test.put(address, "C"), "B");
        assertEquals(test.get(address), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_types() {
        MetaProperty<String> test = CompanyAddress.meta().companyName();
        
        assertEquals(test.metaBean().beanType(), CompanyAddress.class);
        assertEquals(test.propertyType(), String.class);
        assertEquals(test.propertyGenericType(), String.class);
    }

    @Test
    public void test_metaProperty_annotations() {
        MetaProperty<String> prop = CompanyAddress.meta().companyName();
        List<Annotation> test = prop.annotations();
        
        assertEquals(test.size(), 1);
        assertTrue(test.get(0) instanceof PropertyDefinition);
    }

}
