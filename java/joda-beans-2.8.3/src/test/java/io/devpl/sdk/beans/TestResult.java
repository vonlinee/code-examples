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
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import io.devpl.sdk.beans.sample.AbstractResult;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.AddressResult;
import io.devpl.sdk.beans.sample.CompanyAddress;
import io.devpl.sdk.beans.sample.CompanyAddressMidResult;
import io.devpl.sdk.beans.sample.CompanyAddressResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test property using Person.
 */
public class TestResult {

    @Test
    public void test_bean() {
        Bean test = new AddressResult();
        
        assertEquals(test.metaBean(), AddressResult.meta());

        assertTrue(test.propertyNames().contains("docs"));
        assertEquals(test.getProperty("docs").name(), "docs");
        assertEquals(test.toString(), "AddressResult{docs=null, resultType=Address}");
    }

    @Test(expected = NoSuchElementException.class)
    public void test_bean_invalidPropertyName() {
        Bean test = AddressResult.meta().builder().build();
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
        MetaBean test = AddressResult.meta();
        assertEquals(test.beanType(), AddressResult.class);
        assertEquals(test.beanName(), AddressResult.class.getName());
        assertEquals(test.metaPropertyCount(), 2);
        assertTrue(test.metaPropertyExists("docs"));
        assertEquals(test.metaProperty("docs").name(), "docs");
        assertTrue(test.metaPropertyExists("resultType"));
        assertEquals(test.metaProperty("resultType").name(), "resultType");
    }

    @Test(expected = NoSuchElementException.class)
    public void test_metaBean_invalidPropertyName() {
        MetaBean test = AddressResult.meta();
        try {
            test.metaProperty("Rubbish");
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_genericType_abstract() {
        @SuppressWarnings("unchecked")
        AbstractResult.Meta<Address> test = AbstractResult.meta();
        Assert.assertEquals(test.docs().propertyType(), List.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), AbstractResult.class), Address.class);
    }

    @Test
    public void test_genericType_Address() {
        AddressResult obj = new AddressResult();
        AddressResult.Meta test = AddressResult.meta();
        Assert.assertEquals(test.docs().propertyType(), List.class);
        assertEquals(JodaBeanUtils.collectionType(obj.docs()), Address.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), AddressResult.class), Address.class);
    }

    @Test
    public void test_genericType_CompanyAddress() {
        CompanyAddressResult obj = new CompanyAddressResult();
        CompanyAddressResult.Meta test = CompanyAddressResult.meta();
        Assert.assertEquals(test.docs().propertyType(), List.class);
        assertEquals(JodaBeanUtils.collectionType(obj.docs()), CompanyAddress.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), test.docs().declaringType()), Address.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), CompanyAddressResult.class), CompanyAddress.class);
    }

    @Test
    public void test_genericType_CompanyAddressMid() {
        CompanyAddressMidResult obj = new CompanyAddressMidResult();
        CompanyAddressMidResult.Meta test = CompanyAddressMidResult.meta();
        Assert.assertEquals(test.docs().propertyType(), List.class);
        assertEquals(JodaBeanUtils.collectionType(obj.docs()), CompanyAddress.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), test.docs().declaringType()), Address.class);
        assertEquals(JodaBeanUtils.collectionType(test.docs(), CompanyAddressResult.class), CompanyAddress.class);
    }

}
