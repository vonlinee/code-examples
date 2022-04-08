package org.setamv.shardingsphere.starter.controller;

import org.setamv.shardingsphere.starter.dto.OrderDetailByYearDTO;
import org.setamv.shardingsphere.starter.dto.OrderDetailQueryParamsDTO;
import org.setamv.shardingsphere.starter.dto.Result;
import org.setamv.shardingsphere.starter.model.OrderMainByYear;
import org.setamv.shardingsphere.starter.service.OrderDetailByYearService;
import org.setamv.shardingsphere.starter.service.OrderMainByYearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderByYear")
public class OrderByYearController {

    private static final Logger log = LoggerFactory.getLogger(OrderByYearController.class);

    @Autowired
    private OrderMainByYearService orderMainByYearService;

    @Autowired
    private OrderDetailByYearService orderDetailByYearService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Long> add(@RequestBody OrderMainByYear order) {
        Long orderId = orderMainByYearService.add(order);
        return Result.success(orderId);
    }

    @RequestMapping(value = "/get/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<OrderMainByYear> get(@PathVariable Long orderId) {
        return Result.success(orderMainByYearService.get(orderId, true));
    }

    @RequestMapping(value = "/detailListWithMain", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<OrderDetailByYearDTO>> get(OrderDetailQueryParamsDTO params) {
        return Result.success(orderDetailByYearService.listWithMain(params));
    }


}
