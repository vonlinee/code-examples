package sample.seata.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.seata.entity.Order;
import sample.seata.mapper.OrderMapper;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean create(Order order) {
        log.info("创建订单开始");
        int index = orderMapper.insert(order);
        log.info("创建订单结束");
        return index > 0;
    }
}
