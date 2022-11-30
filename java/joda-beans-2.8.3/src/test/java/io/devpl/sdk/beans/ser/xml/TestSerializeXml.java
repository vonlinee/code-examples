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
package io.devpl.sdk.beans.ser.xml;

import static org.junit.Assert.assertEquals;

import java.io.CharArrayWriter;
import java.io.IOException;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.ser.JodaBeanSerializer;
import io.devpl.sdk.beans.ser.SerDeserializers;
import io.devpl.sdk.beans.ser.SerTestHelper;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.sample.Address;
import io.devpl.sdk.beans.sample.ImmAddress;
import io.devpl.sdk.beans.sample.ImmDoubleFloat;
import io.devpl.sdk.beans.sample.ImmEmpty;
import io.devpl.sdk.beans.sample.ImmGenericCollections;
import io.devpl.sdk.beans.sample.ImmGuava;
import io.devpl.sdk.beans.sample.ImmKey;
import io.devpl.sdk.beans.sample.ImmMappedKey;
import io.devpl.sdk.beans.sample.ImmOptional;
import io.devpl.sdk.beans.sample.ImmPerson;
import io.devpl.sdk.beans.sample.JodaConvertBean;
import io.devpl.sdk.beans.sample.JodaConvertInterface;
import io.devpl.sdk.beans.sample.JodaConvertWrapper;
import io.devpl.sdk.beans.sample.Person;
import io.devpl.sdk.beans.sample.SimpleName;
import io.devpl.sdk.beans.sample.SimplePerson;
import io.devpl.sdk.beans.test.BeanAssert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Test property roundtrip using XML.
 */
public class TestSerializeXml {

    @Test
    public void test_writeAddress() {
        Address address = SerTestHelper.testAddress();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(address);
//        System.out.println(xml);
        
        Address bean = (Address) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, address);
    }

    @Test
    public void test_writeToAppendable() throws IOException {
        Address address = SerTestHelper.testAddress();
        CharArrayWriter output = new CharArrayWriter();
        JodaBeanSerializer.PRETTY.xmlWriter().write(address, output);
        String xml = output.toString();

        Address bean = (Address) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
        BeanAssert.assertBeanEquals(bean, address);
    }

    @Test
    public void test_writeImmAddress() {
        ImmAddress address = SerTestHelper.testImmAddress();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(address);
        
        xml = xml.replace("185", "18<!-- comment -->5");
//        System.out.println(xml);
        
        ImmAddress bean = (ImmAddress) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, address);
    }

    @Test
    public void test_writeImmOptional() {
        ImmOptional optional = SerTestHelper.testImmOptional();
        String xml = JodaBeanSerializer.PRETTY.withIncludeDerived(true).xmlWriter().write(optional);
//        System.out.println(xml);
        
        ImmOptional bean = (ImmOptional) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, optional);
    }

    @Test
    public void test_writeCollections() {
        ImmGuava<String> optional = SerTestHelper.testCollections();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(optional);
//        System.out.println(xml);
        
        @SuppressWarnings("unchecked")
        ImmGuava<String> bean = (ImmGuava<String>) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, optional);
    }

    @Test
    public void test_writeJodaConvertInterface() {
        ImmGenericCollections<JodaConvertInterface> array = SerTestHelper.testGenericInterfaces();
        
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(array);
//        System.out.println(xml);
        
        @SuppressWarnings("unchecked")
        ImmGenericCollections<JodaConvertInterface> bean =
                (ImmGenericCollections<JodaConvertInterface>) JodaBeanSerializer.COMPACT.xmlReader().read(xml);
//        System.out.println(bean);
        BeanAssert.assertBeanEquals(bean, array);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_readWriteBeanEmptyChild_pretty() {
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        bean.set("child", ImmEmpty.builder().build());
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(bean);
        assertEquals(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bean type=\"org.joda.beans.impl.flexi.FlexiBean\">\n <element>Test</element>\n <child type=\"org.joda.beans.sample.ImmEmpty\"/>\n</bean>\n");
        FlexiBean parsed = JodaBeanSerializer.PRETTY.xmlReader().read(xml, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWriteBeanEmptyChild_compact() {
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        bean.set("child", ImmEmpty.builder().build());
        String xml = JodaBeanSerializer.COMPACT.xmlWriter().write(bean);
        assertEquals(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.impl.flexi.FlexiBean\"><element>Test</element><child type=\"org.joda.beans.sample.ImmEmpty\"/></bean>");
        FlexiBean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml, FlexiBean.class);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWriteJodaConvertWrapper() {
        JodaConvertWrapper wrapper = new JodaConvertWrapper();
        JodaConvertBean bean = new JodaConvertBean("Hello:9");
        wrapper.setBean(bean);
        wrapper.setDescription("Weird");
        String xml = JodaBeanSerializer.COMPACT.xmlWriter().write(wrapper);
        assertEquals(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.sample.JodaConvertWrapper\"><bean>Hello:9</bean><description>Weird</description></bean>");
        Bean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml);
        BeanAssert.assertBeanEquals(wrapper, parsed);
    }

    @Test
    public void test_readWriteJodaConvertBean() {
        JodaConvertBean bean = new JodaConvertBean("Hello:9");
        String xml = JodaBeanSerializer.COMPACT.xmlWriter().write(bean);
        assertEquals(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.sample.JodaConvertBean\"><base>Hello</base><extra>9</extra></bean>");
        Bean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml);
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_read_primitiveTypeChanged() throws IOException {
        String json = "<bean><a>6</a><b>5</b></bean>}";
        ImmDoubleFloat parsed = JodaBeanSerializer.COMPACT.xmlReader().read(json, ImmDoubleFloat.class);
        assertEquals(6, parsed.getA(), 1e-10);
        assertEquals(5, parsed.getB(), 1e-10);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_read_nonStandard_JodaConvertWrapper_expanded() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.sample.JodaConvertWrapper\"><bean><base>Hello</base><extra>9</extra></bean><description>Weird</description></bean>";
        Bean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml);
        JodaConvertWrapper wrapper = new JodaConvertWrapper();
        JodaConvertBean bean = new JodaConvertBean("Hello:9");
        wrapper.setBean(bean);
        wrapper.setDescription("Weird");
        BeanAssert.assertBeanEquals(wrapper, parsed);
    }

    @Test
    public void test_read_nonStandard_JodaConvertBean_flattened() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.sample.JodaConvertBean\">Hello:9</bean>";
        Bean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml);
        JodaConvertBean bean = new JodaConvertBean("Hello:9");
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_read_nonStandard_withCommentBeanRoot() {
        String xml = "<bean><!-- comment --><element>Test</element></bean>";
        FlexiBean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml, FlexiBean.class);
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_read_nonStandard_withCommentInProperty() {
        String xml = "<bean><element><!-- comment -->Test</element></bean>";
        FlexiBean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml, FlexiBean.class);
        FlexiBean bean = new FlexiBean();
        bean.set("element", "Test");
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_read_aliased() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean type=\"org.joda.beans.sample.SimpleName\">" +
        		"<firstName>A</firstName><givenName>B</givenName></bean>";
        Bean parsed = JodaBeanSerializer.COMPACT.xmlReader().read(xml);
        SimpleName bean = new SimpleName();
        bean.setForename("A");
        bean.setSurname("B");
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_readWriteInterfaceKeyMap() {
        ImmKey key1 = ImmKey.builder().name("Alpha").build();
        ImmPerson person1 = ImmPerson.builder().forename("Bob").surname("Builder").build();
        ImmKey key2 = ImmKey.builder().name("Beta").build();
        ImmPerson person2 = ImmPerson.builder().forename("Dana").surname("Dash").build();
        ImmMappedKey mapped = ImmMappedKey.builder().data(ImmutableMap.of(key1, person1, key2, person2)).build();
        String xml = JodaBeanSerializer.PRETTY.xmlWriter().write(mapped);
        
        ImmMappedKey bean = (ImmMappedKey) JodaBeanSerializer.PRETTY.xmlReader().read(xml);
        BeanAssert.assertBeanEquals(bean, mapped);
    }

    @Test
    public void test_read_badTypeInMap() {
        String xml = "<bean><element metatype=\"Map\"><entry><item>work</item><item type=\"com.foo.UnknownEnum\">BIGWIG</item></entry></element></bean>";
        FlexiBean parsed = JodaBeanSerializer.COMPACT.withDeserializers(SerDeserializers.LENIENT).xmlReader().read(xml, FlexiBean.class);
        FlexiBean bean = new FlexiBean();
        bean.set("element", ImmutableMap.of("work", "BIGWIG"));  // converted to a string
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    @Test
    public void test_read_ignoreProperty() {
        String xml = "<bean><name>foo</name><wibble>ignored</wibble></bean>";
        ImmKey parsed = JodaBeanSerializer.COMPACT.withDeserializers(SerDeserializers.LENIENT).xmlReader().read(xml, ImmKey.class);
        ImmKey bean = ImmKey.builder().name("foo").build();
        BeanAssert.assertBeanEquals(bean, parsed);
    }

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_read_noBeanElementAtRoot() {
        JodaBeanSerializer.COMPACT.xmlReader().read("<foo></foo>", Bean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_read_noTypeAttributeAtRoot() {
        JodaBeanSerializer.COMPACT.xmlReader().read("<bean></bean>", Bean.class);
    }

    @Test
    public void test_read_noTypeAttributeAtRootButTypeSpecified() {
        FlexiBean parsed = JodaBeanSerializer.COMPACT.xmlReader().read("<bean></bean>", FlexiBean.class);
        BeanAssert.assertBeanEquals(new FlexiBean(), parsed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_read_rootTypeAttributeNotBean() {
        JodaBeanSerializer.COMPACT.xmlReader().read("<bean type=\"java.lang.Integer\"></bean>", Bean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_read_rootTypeInvalid() {
        JodaBeanSerializer.COMPACT.xmlReader().read("<bean type=\"org.joda.beans.impl.flexi.FlexiBean\"></bean>", SimplePerson.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_read_rootTypeArgumentInvalid() {
        JodaBeanSerializer.COMPACT.xmlReader().read("<bean></bean>", Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_write_nullKeyInMap() {
        Address address = new Address();
        Person bean = new Person();
        bean.getOtherAddressMap().put(null, address);
        JodaBeanSerializer.COMPACT.xmlWriter().write(bean);
    }

}
