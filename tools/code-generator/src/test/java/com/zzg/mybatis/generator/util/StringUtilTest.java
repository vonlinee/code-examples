package com.zzg.mybatis.generator.util;

import org.junit.Assert;
import org.junit.Test;

import io.devpl.codegen.common.utils.Validator;

/**
 * Created by Owen on 6/18/16.
 */
public class StringUtilTest {

    @Test
    public void testDbStringToCamelStyle() {
        String result = Validator.dbStringToCamelStyle("person_address");
        Assert.assertEquals("PersonAddress", result);
    }

    @Test
    public void testDbStringToCamelStyle_case2() {
        String result = Validator.dbStringToCamelStyle("person_address_name");
        Assert.assertEquals("PersonAddressName", result);
    }

    @Test
    public void testDbStringToCamelStyle_case3() {
        String result = Validator.dbStringToCamelStyle("person_db_name");
        Assert.assertEquals("PersonDbName", result);
    }

    @Test
    public void testDbStringToCamelStyle_case4() {
        String result = Validator.dbStringToCamelStyle("person_jobs_");
        Assert.assertEquals("PersonJobs", result);
    }

    @Test
    public void testDbStringToCamelStyle_case5() {
        String result = Validator.dbStringToCamelStyle("a");
        Assert.assertEquals("A", result);
    }

}
