package io.devpl.webui.controller.config;

import io.devpl.webui.rest.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @PostMapping("/test1.do")
    public Result listAll(@RequestBody(required = false) Map<String, Object> param) {
        Result result = null;
        result.setDescription("描述信息");
        result.setCode(200);
        result.setMessage("正常");
        result.setStackTrace("=========");
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}
