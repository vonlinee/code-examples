package sample.sharding.jdbc.service;

import sample.sharding.jdbc.entity.Order;

public interface IOrderService {

    public Order insertOne(Order order);
}
