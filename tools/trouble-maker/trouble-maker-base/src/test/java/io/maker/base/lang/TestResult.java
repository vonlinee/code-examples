package io.maker.base.lang;

import io.maker.base.collection.ParamMap;
import io.maker.base.rest.OptResult;
import io.maker.base.rest.ResultDescription;

import java.util.Map;

public class TestResult {

    public static void main(String[] args) {
        OptResult.Builder<Map<String, Object>> builder = OptResult.builder();
        builder.code(200);
        builder.data(new ParamMap()).description(200, "");
        builder.description(ResultDescription.custom(200, "", c -> c == 1));
        OptResult<Map<String, Object>> result = builder.build();
        System.out.println(result);
    }

}
