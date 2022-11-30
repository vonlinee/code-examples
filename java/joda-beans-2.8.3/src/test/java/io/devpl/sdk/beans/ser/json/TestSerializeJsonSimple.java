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
package io.devpl.sdk.beans.ser.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.ser.JodaBeanSerializer;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.ImmArrays;
import io.devpl.sdk.beans.sample.ImmDoubleFloat;
import io.devpl.sdk.beans.sample.ImmGuava;
import io.devpl.sdk.beans.sample.ImmOptional;
import io.devpl.sdk.beans.sample.Person;
import io.devpl.sdk.beans.sample.SimpleJson;
import io.devpl.sdk.beans.ser.SerTestHelper;
import io.devpl.sdk.beans.test.BeanAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.io.Resources;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

/**
 * Test property roundtrip using JSON.
 */
@RunWith(DataProviderRunner.class)
public class TestSerializeJsonSimple {

    @Test
    public void test_writeSimpleJson() throws IOException {
        SimpleJson bean = SerTestHelper.testSimpleJson();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
//        System.out.println(json);
        assertEqualsSerialization(json, "/io/devpl/sdk/beans/ser/SimpleJson.simplejson");
        
        SimpleJson parsed = JodaBeanSerializer.PRETTY.simpleJsonReader().read(json, SimpleJson.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_writeImmOptional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        String json = JodaBeanSerializer.PRETTY.withIncludeDerived(true).simpleJsonWriter().write(bean);
//        System.out.println(json);
        assertEqualsSerialization(json, "/io/devpl/sdk/beans/ser/ImmOptional.simplejson");
        
        ImmOptional parsed = JodaBeanSerializer.PRETTY.simpleJsonReader().read(json, ImmOptional.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_writeImmArrays() throws IOException {
        ImmArrays bean = ImmArrays.of(
                new int[] {1, 3, 2},
                new long[] {1, 4, 3},
                new double[] {1.1, 2.2, 3.3},
                new boolean[] {true, false});
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
//        System.out.println(json);
        assertEqualsSerialization(json, "/io/devpl/sdk/beans/ser/ImmArrays.simplejson");
        
        ImmArrays parsed = JodaBeanSerializer.PRETTY.simpleJsonReader().read(json, ImmArrays.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_writeCollections()  throws IOException {
        ImmGuava<String> optional = SerTestHelper.testCollections();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(optional);
//        System.out.println(json);
        assertEqualsSerialization(json, "/io/devpl/sdk/beans/ser/Collections.simplejson");
        
        @SuppressWarnings("unchecked")
        ImmGuava<String> bean = (ImmGuava<String>) JodaBeanSerializer.PRETTY.simpleJsonReader().read(json, ImmGuava.class);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, optional);
    }

    private void assertEqualsSerialization(String json, String expectedResource) throws IOException {
        URL url = TestSerializeJson.class.getResource(expectedResource);
        String expected = Resources.asCharSource(url, StandardCharsets.UTF_8).read();
        assertEquals(json.trim().replace(System.lineSeparator(), "\n"), expected.trim().replace(System.lineSeparator(), "\n"));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_readWriteBeanEmptyChild_pretty() {
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        bean.set("child", new HashMap<String, String>());
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
        assertEquals(json, "{\n \"element\": \"Test\",\n \"child\": {}\n}\n");
        FlexiBean parsed = JodaBeanSerializer.PRETTY.simpleJsonReader().read(json, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWriteBeanEmptyChild_compact() {
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        bean.set("child", new HashMap<String, String>());
        String json = JodaBeanSerializer.COMPACT.simpleJsonWriter().write(bean);
        assertEquals(json, "{\"element\":\"Test\",\"child\":{}}");
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleJsonReader().read(json, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_read_primitiveTypeChanged() throws IOException {
        String json = "{\"a\":6,\"b\":5}";
        ImmDoubleFloat parsed = JodaBeanSerializer.COMPACT.simpleJsonReader().read(json, ImmDoubleFloat.class);
        assertEquals(6, parsed.getA(), 1e-10);
        assertEquals(5, parsed.getB(), 1e-10);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_readWrite_boolean_true() {
        FlexiBean bean = new FlexiBean();
        bean.set("data", Boolean.TRUE);
        String json = JodaBeanSerializer.COMPACT.simpleJsonWriter().write(bean);
        assertEquals(json, "{\"data\":true}");
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleJsonReader().read(new StringReader(json), FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWrite_boolean_false() {
        FlexiBean bean = new FlexiBean();
        bean.set("data", Boolean.FALSE);
        String json = JodaBeanSerializer.COMPACT.simpleJsonWriter().write(bean);
        assertEquals(json, "{\"data\":false}");
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleJsonReader().read(new StringReader(json), FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_read_emptyFlexiBean() {
        FlexiBean parsed = JodaBeanSerializer.COMPACT.simpleJsonReader().read("{}", FlexiBean.class);
        BeanAssert.assertBeanEquals(new FlexiBean(), parsed);
    }

    @Test(expected = ClassCastException.class)
    public void test_read_rootTypeArgumentIncorrect() {
        JodaBeanSerializer.COMPACT.simpleJsonReader().read("{}", Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_write_nullKeyInMap() {
        Address address = new Address();
        Person bean = new Person();
        bean.getOtherAddressMap().put(null, address);
        JodaBeanSerializer.COMPACT.simpleJsonWriter().write(bean);
    }

    //-----------------------------------------------------------------------
    @DataProvider
    public static Object[][] data_badFormat() {
        return new Object[][] {
            {"{,}"},
            {"{1,2}"},
            {"{\"a\",6}"},
            {"{\"a\":[}}"},
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("data_badFormat")
    public void test_badFormat(String text) throws IOException {
        JodaBeanSerializer.COMPACT.simpleJsonReader().read(text, FlexiBean.class);
    }

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_writer_nullSettings() {
        new JodaBeanSimpleJsonWriter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_writer_write1_nullBean() {
        new JodaBeanSimpleJsonWriter(JodaBeanSerializer.PRETTY).write(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_writer_write2_nullBean() throws IOException {
        new JodaBeanSimpleJsonWriter(JodaBeanSerializer.PRETTY).write(null, new StringBuilder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_writer_write2_nullAppendable() throws IOException {
        new JodaBeanSimpleJsonWriter(JodaBeanSerializer.PRETTY).write(new FlexiBean(), null);
    }

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_reader_nullSettings() {
        new JodaBeanSimpleJsonReader(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readReader_null() {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read((Reader) null, FlexiBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readString_null() {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read((String) null, FlexiBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readReaderType_nullReader() throws IOException {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read((Reader) null, Bean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readReaderType_nullType() throws IOException {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read(new StringReader(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readStringType_nullString() throws IOException {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read((String) null, Bean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_reader_readStringType_nullType() throws IOException {
        new JodaBeanSimpleJsonReader(JodaBeanSerializer.PRETTY).read("", null);
    }

}
