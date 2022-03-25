package com.mzd.multipledatasources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzd.multipledatasources.entity.OrderItem;
import com.mzd.multipledatasources.mapper.ordercenter.OrderItemMapper;

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
