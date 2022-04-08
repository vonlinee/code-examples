package org.setamv.shardingsphere.starter.controller;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.setamv.shardingsphere.starter.config.CustomizedShardingDataSource;
import org.setamv.shardingsphere.starter.config.ShardingHelper;
import org.setamv.shardingsphere.starter.dto.OrderDetailDTO;
import org.setamv.shardingsphere.starter.dto.Result;
import org.setamv.shardingsphere.starter.model.OrderMain;
import org.setamv.shardingsphere.starter.service.OrderDetailService;
import org.setamv.shardingsphere.starter.service.OrderMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
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
    private DataSource dataSource;

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

    @RequestMapping(value = "/detailListWithMain/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<OrderDetailDTO>> detailListWithMain(@PathVariable Long orderId) {
        List<OrderDetailDTO> list = orderDetailService.listWithMain(orderId);
        return Result.success(list);
    }

    @RequestMapping(value = "/changeShardingRule", method = RequestMethod.PUT)
    public Result<Boolean> changeShardingRule() throws Exception {
        ShardingRuleConfiguration shardingRuleConfig = ShardingHelper.buildShardingRuleConfig();
        ((CustomizedShardingDataSource)dataSource).refreshShardingRule(shardingRuleConfig);
        return Result.success(true);
    }

}
