package io.maker.web.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.maker.base.io.FileUtils;
import io.maker.base.rest.Result;

@RestController
@RequestMapping("/mybatis")
public class MyBatisController {

    @PostMapping("/insert")
    public Result<Map<String, Object>> insert(Map<String, Object> paramMap) {
        return null;
    }
}
