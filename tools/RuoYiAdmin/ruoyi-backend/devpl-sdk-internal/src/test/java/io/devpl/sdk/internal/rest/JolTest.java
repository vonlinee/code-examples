package io.devpl.sdk.internal.rest;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.Map;

public class JolTest {

    @Test
    public void test1() {
        Result<Object> result1 = new Result<>();
        Result<Map<String, Object>> result2 = new Result<>();

        EntityResult result3 = new EntityResult();

        ClassLayout.parseInstance(result1);
        System.out.println();
        ClassLayout.parseInstance(result2);
        System.out.println();
        ClassLayout.parseInstance(result3);
        System.out.println();
    }
}
