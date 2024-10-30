package org.example.server.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class ApiController {

    @PostMapping("/post")
    public Map<String, Object> post(@RequestBody Map<String, Object> map) {
        return map;
    }
}
