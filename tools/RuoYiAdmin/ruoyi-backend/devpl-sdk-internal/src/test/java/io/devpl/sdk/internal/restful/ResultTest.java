package io.devpl.sdk.internal.restful;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class ResultTest {
    public static void main(String[] args) {

    }

    @Test
    public void test1() {
        EntityResult result = Results.entity();

        ResultBuilder<Map<String, Object>> builder = result.code(200);

    }
}
