package io.maker.base;

import io.maker.base.rest.OptResult;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {
        OptResult<String> result = OptResult.builder()
            .description(200, "Hello", null)
            .build();
        String show = result.show();
        System.out.println(show);
        ArrayList<String> values = new ArrayList<>();
    }
}
