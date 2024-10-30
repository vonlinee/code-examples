package org.example.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${server.port}")
    private Integer port;

    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Map<String, Object> map) {
        map.put("port", this.port);
        return map;
    }

    @GetMapping("/get")
    public Map<String, Object> getById(String id) {
        return new HashMap<>();
    }
}
