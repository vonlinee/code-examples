package sample.sharding.jdbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.sharding.jdbc.entity.Order;
import sample.sharding.jdbc.mapper.OrderMapper;
import sample.sharding.jdbc.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Order insertOne(Order order) {
        int i = orderMapper.insertOrder(order.getPrice(), order.getOrderId(), order.getStatus());
        if (i > 0) {
            return order;
        }
        return null;
    }
}
