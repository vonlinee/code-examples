package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HuaweiYunController {

    @GetMapping("/metric-data")
    public Map<String, Object> get() {
        Map<String, Object> map = new HashMap<>();
        map.put("", "");
        return map;
    }
}
