package com.mzd.multipledatasources.dao.ordercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mzd.multipledatasources.entity.OrderItem;
import com.mzd.multipledatasources.mapper.ordercenter.OrderItemMapper;

@Component
public class OrderItemDao {
	@Autowired
	private OrderItemMapper tm2;

	public void save(OrderItem t) {
		tm2.save(t);
	}
}
