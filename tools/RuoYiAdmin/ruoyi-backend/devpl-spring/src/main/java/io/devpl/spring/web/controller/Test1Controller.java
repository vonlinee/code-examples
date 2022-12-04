package io.devpl.spring.web.controller;

import io.devpl.sdk.rest.*;
import io.devpl.spring.web.mvc.RequestInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class Test1Controller {

    @GetMapping("/testparammap")
    public Map<String, Object> map(@org.springframework.web.bind.annotation.RequestParam Map<String, Object> param) {
        return new HashMap<>();
    }

    @GetMapping("/testparammap1")
    public Map<String, Object> map1(int age, String name, boolean proxy) {
        return new HashMap<>();
    }

    @GetMapping("/testparammap2")
    public Map<String, Object> map2(@org.springframework.web.bind.annotation.RequestParam ModelMap param) {
        return new HashMap<>();
    }

    @RequestMapping("/testparammap5")
    public Map<String, Object> map2(RequestInfo info) {
        return new HashMap<>();
    }

    public ResultTemplate met231() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zs");
        map.put("age", 30);
        map.put("sex", false);
        ResultBuilder<Map<String, Object>> builder = Results.mapBean()
                                                            .code(200)
                                                            .message("消息内容")
                                                            .moreInfo("更多信息")
                                                            .data(map);

        try {
            int i = 1 / 0;
        } catch (Exception exception) {
            builder.throwable(exception);
        }
        Result<Map<String, Object>> result = builder.build();

        ListResult<Result<Map<String, Object>>> result1 = new ListResult<>();
        result1.setCode(200);
        result1.setMessage("提示信息");
        result1.setData(Arrays.asList(result));

        return result1;
    }
}
