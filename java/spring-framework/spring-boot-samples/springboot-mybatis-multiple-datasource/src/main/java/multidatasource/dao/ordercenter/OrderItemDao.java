package multidatasource.dao.ordercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import multidatasource.entity.OrderItem;
import multidatasource.mapper.ordercenter.OrderItemMapper;

@Component
public class OrderItemDao {
	@Autowired
	private OrderItemMapper tm2;

	public void save(OrderItem t) {
		
	}
}
