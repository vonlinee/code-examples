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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.gen.BeanDefinition;
import io.devpl.sdk.beans.gen.PropertyDefinition;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.sample.AbstractResult;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.ClassAnnotation;
import io.devpl.sdk.beans.sample.Person;
import io.devpl.sdk.beans.sample.SimpleAnnotation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test property using Person.
 */
public class TestPerson {

    private static final int NUM_PROPERTIES = 8;
    private static final String FORENAME = "forename";
    private static final String SURNAME = "surname";
    private static final String NUMBER_OF_CARS = "numberOfCars";

    @Test
    public void test_bean() {
        Bean test = Person.meta().builder().build();

        assertTrue(test instanceof Person);
        
        assertEquals(test.metaBean(), Person.meta());

        assertTrue(test.propertyNames().contains(FORENAME));
        assertTrue(test.propertyNames().contains(SURNAME));
        assertTrue(test.propertyNames().contains(NUMBER_OF_CARS));
        assertFalse(test.propertyNames().contains("Rubbish"));
        
        assertEquals(test.getProperty(FORENAME).name(), FORENAME);
        assertEquals(test.getProperty(SURNAME).name(), SURNAME);
        assertEquals(test.getProperty(NUMBER_OF_CARS).name(), NUMBER_OF_CARS);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_bean_invalidPropertyName() {
        Bean test = Person.meta().builder().build();
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
        MetaBean test = Person.meta();

        assertTrue(test.isBuildable());
        assertEquals(test.beanType(), Person.class);
        assertEquals(test.beanName(), Person.class.getName());
        
        assertEquals(test.metaPropertyCount(), NUM_PROPERTIES);

        assertTrue(test.metaPropertyExists(FORENAME));
        assertTrue(test.metaPropertyExists(SURNAME));
        assertTrue(test.metaPropertyExists(NUMBER_OF_CARS));
        assertFalse(test.metaPropertyExists("Rubbish"));
        
        assertEquals(test.metaProperty(FORENAME).name(), FORENAME);
        assertEquals(test.metaProperty(SURNAME).name(), SURNAME);
        assertEquals(test.metaProperty(NUMBER_OF_CARS).name(), NUMBER_OF_CARS);
        
        Map<String, MetaProperty<?>> map = test.metaPropertyMap();
        assertEquals(map.size(), NUM_PROPERTIES);
        assertTrue(map.containsKey(FORENAME));
        assertTrue(map.containsKey(SURNAME));
        assertTrue(map.containsKey(NUMBER_OF_CARS));
    }

    @Test(expected = NoSuchElementException.class)
    public void test_metaBean_invalidPropertyName() {
        MetaBean test = Person.meta();
        try {
            test.metaProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void test_metaBean_abstract() {
        MetaBean test = AbstractResult.meta();

        assertFalse(test.isBuildable());
        assertEquals(test.beanType(), AbstractResult.class);
        assertEquals(test.beanName(), AbstractResult.class.getName());
        
        assertEquals(test.metaPropertyCount(), 2);

        assertTrue(test.metaPropertyExists("docs"));
        assertFalse(test.metaPropertyExists("Rubbish"));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedPropertyMethod() {
        Person person = new Person();
        Property<String> test = person.forename();
        
        assertSame(test.bean(), person);
        assertSame(test.metaProperty(), Person.meta().forename());

        assertNull(test.get());
        person.setForename("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_property_String() {
        Person person = new Person();
        Property<String> test = person.getProperty(FORENAME);
        
        assertSame(test.bean(), person);
        assertSame(test.metaProperty(), Person.meta().forename());

        assertNull(test.get());
        person.setForename("A");
        assertEquals(test.get(), "A");
        test.set("B");
        assertEquals(test.get(), "B");
        assertEquals(test.put("C"), "B");
        assertEquals(test.get(), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_namedMetaPropertyMethod() {
        Person person = new Person();
        MetaProperty<String> test = Person.meta().forename();
        
        assertEquals(test.metaBean().beanType(), Person.class);
        assertEquals(test.propertyType(), String.class);
        assertSame(test.name(), FORENAME);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(person));
        person.setForename("A");
        assertEquals(test.get(person), "A");
        test.set(person, "B");
        assertEquals(test.get(person), "B");
        assertEquals(test.put(person, "C"), "B");
        assertEquals(test.get(person), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_String() {
        Person person = new Person();
        MetaProperty<String> test = Person.meta().metaProperty(FORENAME);
        
        assertEquals(test.metaBean().beanType(), Person.class);
        assertEquals(test.propertyType(), String.class);
        assertSame(test.name(), FORENAME);
        assertEquals(test.style(), PropertyStyle.READ_WRITE);

        assertNull(test.get(person));
        person.setForename("A");
        assertEquals(test.get(person), "A");
        test.set(person, "B");
        assertEquals(test.get(person), "B");
        assertEquals(test.put(person, "C"), "B");
        assertEquals(test.get(person), "C");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_types_addressList() {
        MetaProperty<List<Address>> test = Person.meta().addressList();
        
        assertEquals(test.metaBean().beanType(), Person.class);
        assertEquals(test.propertyType(), List.class);
        assertTrue(test.propertyGenericType() instanceof ParameterizedType);
        ParameterizedType pt = (ParameterizedType) test.propertyGenericType();
        assertEquals(pt.getRawType(), List.class);
        assertNull(pt.getOwnerType());
        Type[] actualTypes = pt.getActualTypeArguments();
        assertEquals(actualTypes.length, 1);
        assertEquals(actualTypes[0], Address.class);
    }

    @Test
    public void test_BeanUtils_addressList() {
        MetaProperty<List<Address>> test = Person.meta().addressList();
        
        assertEquals(test.metaBean().beanType(), Person.class);
        assertEquals(test.propertyType(), List.class);
        assertTrue(test.propertyGenericType() instanceof ParameterizedType);
        ParameterizedType pt = (ParameterizedType) test.propertyGenericType();
        assertEquals(pt.getRawType(), List.class);
        assertNull(pt.getOwnerType());
        Type[] actualTypes = pt.getActualTypeArguments();
        assertEquals(actualTypes.length, 1);
        assertEquals(actualTypes[0], Address.class);
    }

    @Test
    public void test_metaProperty_types_otherAddressMap() {
        MetaProperty<Map<String, Address>> test = Person.meta().otherAddressMap();
        
        assertEquals(test.metaBean().beanType(), Person.class);
        assertEquals(test.propertyType(), Map.class);
        assertTrue(test.propertyGenericType() instanceof ParameterizedType);
        ParameterizedType pt = (ParameterizedType) test.propertyGenericType();
        assertEquals(pt.getRawType(), Map.class);
        assertNull(pt.getOwnerType());
        Type[] actualTypes = pt.getActualTypeArguments();
        assertEquals(actualTypes.length, 2);
        assertEquals(actualTypes[0], String.class);
        assertEquals(actualTypes[1], Address.class);
    }

    @Test
    public void test_metaProperty_annotations_addressList() {
        MetaProperty<List<Address>> prop = Person.meta().addressList();
        List<Annotation> test = prop.annotations();
        
        assertEquals(test.size(), 1);
        assertTrue(test.get(0) instanceof PropertyDefinition);
    }

    @Test
    public void test_metaProperty_annotations_extensions() {
        MetaProperty<FlexiBean> prop = Person.meta().extensions();
        List<Annotation> annos = prop.annotations();
        
        assertEquals(annos.size(), 2);
        assertTrue(annos.get(0) instanceof PropertyDefinition);
        assertTrue(annos.get(1) instanceof SimpleAnnotation);
        assertEquals(prop.annotation(PropertyDefinition.class).get(), "smart");
    }

    @Test
    public void test_metaBean_annotations() {
        Person.Meta meta = Person.meta();
        List<Annotation> annos = meta.annotations();
        
        assertEquals(annos.size(), 2);
        assertTrue(annos.get(0) instanceof BeanDefinition);
        assertTrue(annos.get(1) instanceof ClassAnnotation);
        assertEquals(meta.annotation(BeanDefinition.class).builderScope(), "smart");
    }

}
