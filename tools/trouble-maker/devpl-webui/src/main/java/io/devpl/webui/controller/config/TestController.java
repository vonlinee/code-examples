package io.devpl.webui.controller.config;

import io.devpl.webui.rest.ListResult;
import io.devpl.webui.rest.Result;
import io.devpl.webui.rest.ResultTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @PostMapping("/test1.do")
    public Object listAll(@RequestBody(required = false) Map<String, Object> param) {
        ListResult<Map<String, Object>> listResult = new ListResult<>();

        return new Object();
    }
}
