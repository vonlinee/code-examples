package org.setamv.shardingsphere.starter.controller;

import org.setamv.shardingsphere.starter.dto.OrderDetailDTO;
import org.setamv.shardingsphere.starter.dto.OrderIdConditionDTO;
import org.setamv.shardingsphere.starter.dto.OrderPaymentDTO;
import org.setamv.shardingsphere.starter.dto.Result;
import org.setamv.shardingsphere.starter.model.OrderMain;
import org.setamv.shardingsphere.starter.service.OrderDetailService;
import org.setamv.shardingsphere.starter.service.OrderMainService;
import org.setamv.shardingsphere.starter.service.OrderPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderMainService orderMainService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderPaymentService orderPaymentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Long> add(@RequestBody OrderMain order) {
        Long id = orderMainService.add(order);
        return Result.success(id);
    }

    @RequestMapping(value = "/get/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<OrderMain> get(@PathVariable Long orderId) {
        OrderMain orderMain = orderMainService.get(orderId, true);
        return Result.success(orderMain);
    }

    @RequestMapping(value = "/getDetailListWithMainByOrderId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<OrderDetailDTO>> getDetailListWithMainByOrderId(@RequestBody OrderIdConditionDTO condition) {
        List<OrderDetailDTO> list = orderDetailService.listWithMain(condition);
        return Result.success(list);
    }

    @RequestMapping(value = "/getPaymentWithMainByOrderId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<OrderPaymentDTO> getPaymentWithMainByOrderId(@RequestBody OrderIdConditionDTO condition) {
        OrderPaymentDTO paymentDTO = orderPaymentService.getWithMain(condition);
        return Result.success(paymentDTO);
    }

}
