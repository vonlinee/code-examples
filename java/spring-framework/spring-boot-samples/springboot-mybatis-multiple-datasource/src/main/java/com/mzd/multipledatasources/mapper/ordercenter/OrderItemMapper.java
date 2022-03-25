package com.mzd.multipledatasources.mapper.ordercenter;

import org.springframework.stereotype.Repository;

import com.mzd.multipledatasources.entity.OrderItem;

@Repository
public interface OrderItemMapper {
	void save(OrderItem t);
}
