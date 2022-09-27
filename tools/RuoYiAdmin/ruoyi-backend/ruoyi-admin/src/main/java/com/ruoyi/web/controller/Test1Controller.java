package com.ruoyi.web.controller;

import io.devpl.spring.web.utils.ParamMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class Test1Controller {

    @GetMapping("/testparammap")
    public Map<String, Object> map(ParamMap param) {
        return new HashMap<>();
    }
}
