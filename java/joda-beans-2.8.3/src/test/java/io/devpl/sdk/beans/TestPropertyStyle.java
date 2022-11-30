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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test PropertyStyle.
 */
public class TestPropertyStyle {

    @Test
    public void test_READ_ONLY() {
        assertTrue(PropertyStyle.READ_ONLY.isReadable());
        assertFalse(PropertyStyle.READ_ONLY.isWritable());
        assertFalse(PropertyStyle.READ_ONLY.isBuildable());
        assertTrue(PropertyStyle.READ_ONLY.isReadOnly());
        assertFalse(PropertyStyle.READ_ONLY.isDerived());
        assertFalse(PropertyStyle.READ_ONLY.isSerializable());
    }

    @Test
    public void test_READ_WRITE() {
        assertTrue(PropertyStyle.READ_WRITE.isReadable());
        assertTrue(PropertyStyle.READ_WRITE.isWritable());
        assertTrue(PropertyStyle.READ_WRITE.isBuildable());
        assertFalse(PropertyStyle.READ_WRITE.isReadOnly());
        assertFalse(PropertyStyle.READ_WRITE.isDerived());
        assertTrue(PropertyStyle.READ_WRITE.isSerializable());
    }

    @Test
    public void test_WRITE_ONLY() {
        assertFalse(PropertyStyle.WRITE_ONLY.isReadable());
        assertTrue(PropertyStyle.WRITE_ONLY.isWritable());
        assertTrue(PropertyStyle.WRITE_ONLY.isBuildable());
        assertFalse(PropertyStyle.WRITE_ONLY.isReadOnly());
        assertFalse(PropertyStyle.WRITE_ONLY.isDerived());
        assertFalse(PropertyStyle.WRITE_ONLY.isSerializable());
    }

    @Test
    public void test_DERIVED() {
        assertTrue(PropertyStyle.DERIVED.isReadable());
        assertFalse(PropertyStyle.DERIVED.isWritable());
        assertFalse(PropertyStyle.DERIVED.isBuildable());
        assertTrue(PropertyStyle.DERIVED.isReadOnly());
        assertTrue(PropertyStyle.DERIVED.isDerived());
        assertFalse(PropertyStyle.DERIVED.isSerializable());
    }

    @Test
    public void test_IMMUTABLE() {
        assertTrue(PropertyStyle.IMMUTABLE.isReadable());
        assertFalse(PropertyStyle.IMMUTABLE.isWritable());
        assertTrue(PropertyStyle.IMMUTABLE.isBuildable());
        assertTrue(PropertyStyle.IMMUTABLE.isReadOnly());
        assertFalse(PropertyStyle.IMMUTABLE.isDerived());
        assertTrue(PropertyStyle.IMMUTABLE.isSerializable());
    }

}
