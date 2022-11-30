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
package io.devpl.sdk.beans.ser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.ImmAddress;
import io.devpl.sdk.beans.sample.ImmEmpty;
import io.devpl.sdk.beans.sample.ImmGuava;
import io.devpl.sdk.beans.sample.ImmOptional;
import io.devpl.sdk.beans.sample.SimpleJson;
import io.devpl.sdk.beans.test.BeanAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.primitives.Bytes;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

/**
 * Test smart reader.
 */
@RunWith(DataProviderRunner.class)
public class TestSerializeSmartReader {

    @Test
    public void test_binary_address()  throws IOException {
        Address bean = SerTestHelper.testAddress();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriter().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    @Test
    public void test_binary_immAddress()  throws IOException {
        ImmAddress bean = SerTestHelper.testImmAddress();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriter().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    @Test
    public void test_binary_optional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriter().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    @Test
    public void test_binary_collections()  throws IOException {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriter().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_binaryReferencing_optional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriterReferencing().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    @Test
    public void test_binaryReferencing_collections()  throws IOException {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        byte[] bytes = JodaBeanSerializer.PRETTY.binWriterReferencing().write(bean);
        Bean roundtrip = JodaBeanSerializer.PRETTY.smartReader().read(bytes);
        BeanAssert.assertBeanEquals(bean, roundtrip);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_json_address()  throws IOException {
        Address bean = SerTestHelper.testAddress();
        String json = JodaBeanSerializer.PRETTY.jsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, Address.class);
    }

    @Test
    public void test_json_immAddress()  throws IOException {
        ImmAddress bean = SerTestHelper.testImmAddress();
        String json = JodaBeanSerializer.PRETTY.jsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmAddress.class);
    }

    @Test
    public void test_json_optional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        String json = JodaBeanSerializer.PRETTY.jsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmOptional.class);
    }

    @Test
    public void test_json_collections()  throws IOException {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        String json = JodaBeanSerializer.PRETTY.jsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmGuava.class);
    }

    @Test
    public void test_json_minimal() throws IOException {
        assertTrue(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(new byte[] {'{', '}'}));
        assertTrue(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(new byte[] {'{', '\n', ' ', '}'}));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_simpleJson_empty()  throws IOException {
        ImmEmpty bean = ImmEmpty.builder().build();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmEmpty.class);
    }

    @Test
    public void test_simpleJson_basic()  throws IOException {
        SimpleJson bean = SerTestHelper.testSimpleJson();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, SimpleJson.class);
    }

    @Test
    public void test_simpleJson_optional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmOptional.class);
    }

    @Test
    public void test_simpleJson_collections()  throws IOException {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        String json = JodaBeanSerializer.PRETTY.simpleJsonWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, json, bean, ImmGuava.class);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_xml_address()  throws IOException {
        Address bean = SerTestHelper.testAddress();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, xml, bean, Address.class);
    }

    @Test
    public void test_xml_immAddress()  throws IOException {
        ImmAddress bean = SerTestHelper.testImmAddress();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, xml, bean, ImmAddress.class);
    }

    @Test
    public void test_xml_optional()  throws IOException {
        ImmOptional bean = SerTestHelper.testImmOptional();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, xml, bean, ImmOptional.class);
    }

    @Test
    public void test_xml_collections()  throws IOException {
        ImmGuava<String> bean = SerTestHelper.testCollections();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(bean);
        assertCharsets(JodaBeanSerializer.PRETTY, xml, bean, ImmGuava.class);
    }

    @Test
    public void test_xml_minimal() throws IOException {
        byte[] bytes = "<bean></bean>".getBytes(StandardCharsets.UTF_8);
        assertTrue(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(bytes));
        assertTrue(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(bytes));
    }

    //-----------------------------------------------------------------------
    private static <T extends Bean> void assertCharsets(JodaBeanSerializer settings, String text, T bean, Class<T> type) {
        byte[] json8Bytes = text.getBytes(StandardCharsets.UTF_8);
        assertTrue(settings.smartReader().isKnownFormat(json8Bytes));
        T smart = settings.smartReader().read(json8Bytes, type);
        BeanAssert.assertBeanEquals(bean, smart);

        byte[] utf8Bytes = Bytes.concat(new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xbf}, json8Bytes);
        assertTrue(settings.smartReader().isKnownFormat(utf8Bytes));
        T smart8 = settings.smartReader().read(utf8Bytes, type);
        BeanAssert.assertBeanEquals(bean, smart8);
    }

    //-----------------------------------------------------------------------
    @DataProvider
    public static Object[][] data_badFormat() {
        return new Object[][] {
            {"xml"},
            {"<beax"},
            {"{,}"},
            {"{  \t\r\n "},
            {"{1,2}"},
            {"{\"a\",6}"},
            {"{\"a\":[}}"},
            {""},
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("data_badFormat")
    public void test_badFormat(String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        JodaBeanSerializer.COMPACT.smartReader().read(bytes, FlexiBean.class);
    }

    @UseDataProvider("data_badFormat")
    public void test_isKnownFormat_false(String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(bytes));
    }

    @Test
    public void test_isKnownFormat_utf8_wrong() throws IOException {
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xbf, '?'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xb0, '<'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xef, (byte) 0xb0, (byte) 0xbf, '<'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xe0, (byte) 0xbb, (byte) 0xbf, '<'}));
    }

    @Test
    public void test_isKnownFormat_utf16le_wrong() throws IOException {
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xff, (byte) 0xfe, '?', (byte) 0x00}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xff, (byte) 0xfe, '<', (byte) 0x01}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xff, (byte) 0xf0, '<', (byte) 0x00}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xf0, (byte) 0xfe, '<', (byte) 0x00}));
    }

    @Test
    public void test_isKnownFormat_utf16be_wrong() throws IOException {
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xfe, (byte) 0xff, (byte) 0x00, '?'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xfe, (byte) 0xff, (byte) 0x01, '<'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xfe, (byte) 0xf0, (byte) 0x00, '<'}));
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(
                new byte[] {(byte) 0xf0, (byte) 0xff, (byte) 0x00, '<'}));
    }

    @Test
    public void test_isKnownFormat_binary_false() throws IOException {
        byte[] bytes = new byte[] {(byte) 0x92, (byte) 0x00};
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(bytes));
    }

    @Test
    public void test_isKnownFormat_binaryRef_false() throws IOException {
        byte[] bytes = new byte[] {(byte) 0x94, (byte) 0x00};
        assertFalse(JodaBeanSerializer.COMPACT.smartReader().isKnownFormat(bytes));
    }

}
