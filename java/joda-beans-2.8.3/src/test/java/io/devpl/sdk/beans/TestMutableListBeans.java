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
import static org.junit.Assert.assertNull;

import io.devpl.sdk.beans.sample.MutableListFinalBean;
import io.devpl.sdk.beans.sample.MutableListNonFinalBean;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Test list with builder.
 */
public class TestMutableListBeans {

    @Test
    public void test_finalBean_noList() {
        MutableListFinalBean test = MutableListFinalBean.builder().build();
        assertNull(test.getStrings());
    }

    @Test
    public void test_finalBean_list() {
        MutableListFinalBean test = MutableListFinalBean.builder().strings("A", "B").build();
        assertEquals(test.getStrings(), ImmutableList.of("A", "B"));
    }

    @Test
    public void test_nonFinalBean_noList() {
        MutableListNonFinalBean test = MutableListNonFinalBean.builder().build();
        assertNull(test.getStrings());
    }

    @Test
    public void test_nonFinalBean_list() {
        MutableListNonFinalBean test = MutableListNonFinalBean.builder().strings("A", "B").build();
        assertEquals(test.getStrings(), ImmutableList.of("A", "B"));
    }

}
