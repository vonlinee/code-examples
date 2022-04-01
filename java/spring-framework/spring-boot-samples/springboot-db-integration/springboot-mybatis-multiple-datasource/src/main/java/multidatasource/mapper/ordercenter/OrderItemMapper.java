package multidatasource.mapper.ordercenter;

import org.springframework.stereotype.Repository;

import multidatasource.entity.OrderItem;

@Repository
public interface OrderItemMapper {
	void saveOne(OrderItem t);
}
