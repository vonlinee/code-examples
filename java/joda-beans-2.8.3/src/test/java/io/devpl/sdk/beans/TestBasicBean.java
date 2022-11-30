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

import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.CompanyAddress;
import io.devpl.sdk.beans.sample.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test BasicBean.
 */
public class TestBasicBean {

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void test_equals() {
        Person a1 = new Person();
        Person a2 = new Person();
        Person b = new Person();
        
        a1.setForename("Stephen");
        a2.setForename("Stephen");
        b.setForename("Etienne");

        assertTrue(a1.equals(a1));
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
        assertTrue(a2.equals(a2));

        assertFalse(a1.equals(b));
        assertFalse(b.equals(a1));

        assertFalse(b.equals("Weird type"));
        assertFalse(b.equals(null));
    }

    @Test
    public void test_hashCode() {
        Person a1 = new Person();
        Person a2 = new Person();
        
        a1.setForename("Stephen");
        a2.setForename("Stephen");
        
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void test_toString() {
        Person a = new Person();
        a.setForename("Stephen");
        a.setSurname("Colebourne");

        assertTrue(a.toString().startsWith("Person{"));
        assertTrue(a.toString().endsWith("}"));
        assertTrue(a.toString().contains("forename=Stephen"));
        assertTrue(a.toString().contains("surname=Colebourne"));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_property_equals() {
        Address obj1 = new Address();
        CompanyAddress obj2 = new CompanyAddress();
        Property<String> p1 = obj1.city();
        Property<String> p2 = obj2.city();
        
        obj1.setCity("London");
        obj2.setCity("London");
        
        assertEquals(p1, p2);
    }

    @Test
    public void test_property_hashCode() {
        Person obj1 = new Person();
        Person obj2 = new Person();
        Property<String> p1 = obj1.forename();
        Property<String> p2 = obj2.forename();
        
        obj1.setForename("Stephen");
        obj2.setForename("Stephen");
        
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void test_property_toString() {
        Person obj1 = new Person();
        Property<String> p1 = obj1.forename();
        
        obj1.setForename("Stephen");
        
        assertEquals(p1.toString(), "Person:forename=Stephen");
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaProperty_equals() {
        MetaProperty<String> p1 = Address.meta().city();
        MetaProperty<String> p2 = CompanyAddress.meta().city();
        
        assertEquals(p1, p2);
    }

    @Test
    public void test_metaProperty_hashCode() {
        MetaProperty<String> p1 = Person.meta().forename();
        MetaProperty<String> p2 = Person.meta().forename();
        
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void test_metaProperty_toString() {
        MetaProperty<String> mp1 = Person.meta().forename();
        
        assertEquals(mp1.toString(), "Person:forename");
    }

}
