package org.example.order.controller;

import org.example.order.remote.FeignPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    @Qualifier("p1")
    FeignPaymentService paymentService;

    @PostMapping("/create")
    public Map<String, Object> create(Map<String, Object> map) {
        long start = System.currentTimeMillis();
        Map<String, Object> result = paymentService.createPayment(map);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
        start = System.currentTimeMillis();
        Map<String, Object> result2 = paymentService.createPayment(map);
        end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
        return result;
    }
}
