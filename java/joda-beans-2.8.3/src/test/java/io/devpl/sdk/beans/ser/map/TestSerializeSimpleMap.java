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
package io.devpl.sdk.beans.ser.map;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import io.devpl.sdk.beans.ser.JodaBeanSerializer;
import io.devpl.sdk.beans.ser.SerTestHelper;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.ImmGuava;
import io.devpl.sdk.beans.sample.ImmOptional;
import io.devpl.sdk.beans.sample.Person;
import io.devpl.sdk.beans.sample.SimpleJson;
import io.devpl.sdk.beans.test.BeanAssert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Test property roundtrip using JSON.
 */
public class TestSerializeSimpleMap {

    @Test
    public void test_writeSimpleJson() {
        SimpleJson bean = SerTestHelper.testSimpleJson();
        Map<String, Object> map = JodaBeanSerializer.PRETTY.simpleMapWriter().write(bean);
//        System.out.println(map);
        
        SimpleJson parsed = JodaBeanSerializer.PRETTY.simpleMapReader().read(map, SimpleJson.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_writeImmOptional() {
        ImmOptional bean = SerTestHelper.testImmOptional();
        Map<String, Object> map = JodaBeanSerializer.PRETTY.withIncludeDerived(true).simpleMapWriter().write(bean);
//        System.out.println(map);
        
        ImmOptional parsed = JodaBeanSerializer.PRETTY.simpleMapReader().read(map, ImmOptional.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_writeCollections() {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        Map<String, Object> map = JodaBeanSerializer.PRETTY.simpleMapWriter().write(bean);
//        System.out.println(map);
        
        @SuppressWarnings("unchecked")
        ImmGuava<String> parsed = (ImmGuava<String>) JodaBeanSerializer.PRETTY.simpleMapReader().read(map, ImmGuava.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_readWriteBeanEmptyChild() {
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        bean.set("child", new HashMap<String, String>());
        Map<String, Object> map = JodaBeanSerializer.PRETTY.simpleMapWriter().write(bean);
        assertEquals(map, ImmutableMap.of("element", "Test", "child", ImmutableMap.of()));
        FlexiBean parsed = JodaBeanSerializer.PRETTY.simpleMapReader().read(map, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_readWrite_boolean_true() {
        FlexiBean bean = new FlexiBean();
        bean.set("data", Boolean.TRUE);
        Map<String, Object> map = JodaBeanSerializer.COMPACT.simpleMapWriter().write(bean);
        assertEquals(map, ImmutableMap.of("data", Boolean.TRUE));
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleMapReader().read(map, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWrite_boolean_false() {
        FlexiBean bean = new FlexiBean();
        bean.set("data", Boolean.FALSE);
        Map<String, Object> map = JodaBeanSerializer.COMPACT.simpleMapWriter().write(bean);
        assertEquals(map, ImmutableMap.of("data", Boolean.FALSE));
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleMapReader().read(map, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_read_emptyFlexiBean() {
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleMapReader().read(new HashMap<>(), FlexiBean.class);
        BeanAssert.assertBeanEquals(new FlexiBean(), parsed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_read_rootTypeArgumentIncorrect() {
        JodaBeanSerializer.COMPACT.simpleMapReader().read(new HashMap<>(), Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_write_nullKeyInMap() {
        Address address = new Address();
        Person bean = new Person();
        bean.getOtherAddressMap().put(null, address);
        JodaBeanSerializer.COMPACT.simpleMapWriter().write(bean);
    }

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_writer_nullSettings() {
        new JodaBeanSimpleMapWriter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_writer_write_nullBean() {
        new JodaBeanSimpleMapWriter(JodaBeanSerializer.PRETTY).write(null);
    }

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_reader_nullSettings() {
        new JodaBeanSimpleMapReader(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_read_nullBean() {
        new JodaBeanSimpleMapReader(JodaBeanSerializer.PRETTY).read(null, FlexiBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_read_nullType() {
        new JodaBeanSimpleMapReader(JodaBeanSerializer.PRETTY).read(new HashMap<>(), null);
    }

}
