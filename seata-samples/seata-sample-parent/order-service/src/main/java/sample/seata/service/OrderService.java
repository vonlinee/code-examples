package sample.seata.service;

import sample.seata.entity.Order;

public interface OrderService {
    boolean create(Order order);
}
