package multidatasource.dao.ordercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import multidatasource.entity.Order;
import multidatasource.mapper.ordercenter.OrderItemMapper;

@Component
public class OrderItemDao {
	@Autowired
	private OrderItemMapper tm2;

	public void save(Order t) {
		tm2.save(t);
	}
}
