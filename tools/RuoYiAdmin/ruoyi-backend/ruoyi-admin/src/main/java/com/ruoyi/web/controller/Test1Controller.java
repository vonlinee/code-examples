package com.ruoyi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class Test1Controller {

    @GetMapping("/testparammap")
    public Map<String, Object> map(Param param) {
        return new HashMap<>();
    }
}
