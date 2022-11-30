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
import static org.junit.Assert.assertNotNull;

import io.devpl.sdk.beans.sample.ImmPerson;
import io.devpl.sdk.beans.sample.MetaBeanLoad;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.impl.map.BeanMap;
import org.junit.Test;

/**
 * Test MetaBean statics.
 */
public class TestMetaBean {

    //-----------------------------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void test_registerMetaBean() {
        // register once OK
        assertNotNull(ImmPerson.meta());
        // register second time not OK
        MetaBean.register(ImmPerson.meta());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_metaBean() {
        MetaBean metaBean = MetaBean.of(MetaBeanLoad.class);
        assertNotNull(metaBean);
        assertEquals(metaBean, MetaBeanLoad.meta());
    }

    @Test
    public void test_metaBean_FlexiBean() {
        assertEquals(MetaBean.of(FlexiBean.class).builder().build().getClass(), FlexiBean.class);
    }

    @Test
    public void test_metaBean_MapBean() {
        assertEquals(MetaBean.of(BeanMap.class).builder().build().getClass(), BeanMap.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_metaBean_notFound() {
        MetaBean.of(String.class);
    }

}
