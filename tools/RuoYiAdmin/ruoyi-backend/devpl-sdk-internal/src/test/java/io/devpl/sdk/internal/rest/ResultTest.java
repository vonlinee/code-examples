package io.devpl.sdk.internal.rest;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ResultTest {
    public static void main(String[] args) {
        ResultBuilder<Map<String, Object>> result = new Result<>();
        result.code(200).message("提示信息").description("正常").stackTrace("堆栈信息");
        ResultTemplate template = result.build();
        System.out.println(JSONUtil.toJsonPrettyStr(template));
    }

    @Test
    public void test1() {
        
    }
}
