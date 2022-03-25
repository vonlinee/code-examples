package multidatasource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import multidatasource.entity.OrderItem;
import multidatasource.mapper.ordercenter.OrderItemMapper;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemMapper orderItemMapper;

	@Transactional
	public void saveOne(OrderItem order) {
		orderItemMapper.saveOne(order);
	}
}
