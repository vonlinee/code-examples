package io.maker.web.controller;

import io.maker.base.collection.ParamMap;
import io.maker.base.rest.OptResult;
import io.maker.base.rest.Result;
import io.maker.base.rest.ResultDescription;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mybatis")
public class MyBatisController {

    @PostMapping("/insert")
    public Result<Map<String, Object>> insert(Map<String, Object> paramMap) {

        return null;
    }
}
