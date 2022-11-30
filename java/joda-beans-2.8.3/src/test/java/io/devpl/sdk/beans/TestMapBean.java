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

import io.devpl.sdk.beans.impl.map.BeanMap;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test MapBean.
 */
public class TestMapBean {

    @Test
    public void test_clone() {
        BeanMap a = new BeanMap();
        a.put("A", "AA");
        a.put("B", "BB");
        BeanMap b = a.clone();
        
        assertEquals(a.get("A"), "AA");
        assertEquals(a.get("B"), "BB");
        assertEquals(b.get("A"), "AA");
        assertEquals(b.get("B"), "BB");
        
        a.clear();

        assertNull(a.get("A"));
        assertNull(a.get("B"));
        assertEquals(b.get("A"), "AA");
        assertEquals(b.get("B"), "BB");
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void test_equalsHashCode() {
        BeanMap a1 = new BeanMap();
        BeanMap a2 = new BeanMap();
        BeanMap b = new BeanMap();
        
        a1.put("first", "A");
        a2.put("first", "A");
        b.put("first", "B");

        assertTrue(a1.equals(a1));
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
        assertTrue(a2.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());

        assertFalse(a1.equals(b));
        assertFalse(b.equals(a1));

        assertFalse(b.equals("Weird type"));
        assertFalse(b.equals(null));
    }

    @Test
    public void test_propertyDefine_propertyRemove() {
        BeanMap mapBean = new BeanMap();
        assertEquals(mapBean.propertyNames().size(), 0);
        mapBean.defineProperty("name", String.class);
        assertEquals(mapBean.propertyNames().size(), 1);
        Property<Object> prop = mapBean.getProperty("name");
        assertEquals(prop.name(), "name");
        assertNull(prop.get());
        mapBean.removeProperty("name");
        assertEquals(mapBean.propertyNames().size(), 0);
    }

    @Test
    public void test_metaBean() {
        BeanMap mapBean = new BeanMap();
        DynamicMetaBean meta = mapBean.metaBean();
        assertEquals(meta.metaPropertyCount(), 0);
        
        meta.metaPropertyDefine("name", String.class);
        assertEquals(meta.metaPropertyCount(), 1);
        MetaProperty<Object> prop = meta.metaProperty("name");
        assertEquals(prop.name(), "name");
        assertNull(prop.get(mapBean));
        
        meta.metaPropertyDefine("name", String.class);
        assertEquals(meta.metaPropertyCount(), 1);
        
        MetaProperty<Object> prop2 = meta.metaProperty("address");
        assertNotNull(prop2);
        assertEquals(meta.metaPropertyCount(), 1);  // meta-property object created but data not changed
    }

}
