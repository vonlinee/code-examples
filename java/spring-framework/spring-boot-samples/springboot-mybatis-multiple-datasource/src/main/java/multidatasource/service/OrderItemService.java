package multidatasource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import multidatasource.entity.OrderItem;
import multidatasource.mapper.ordercenter.OrderItemMapper;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemMapper ts2;

	@Transactional
	public void saveTeacher(OrderItem t) {
		
	}

	@Transactional
	public void saveTeacher2(OrderItem t) {
		int i = 1 / 0;
		ts2.save(t);
	}
}
