package sample.sharding.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.sharding.jdbc.entity.Order;
import sample.sharding.jdbc.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @GetMapping("/insert")
    public Order insert(Order order) {
        return orderService.insertOne(order);
    }
}
