package com.mzd.multipledatasources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzd.multipledatasources.dao.ordercenter.OrderItemDao;
import com.mzd.multipledatasources.dao.productcenter.ProductDao;
import com.mzd.multipledatasources.entity.Product;

@Service
public class ProductService {
	@Autowired
	private ProductDao ts1;
	@Autowired
	private OrderItemService ts2;
	@Autowired
	private OrderItemDao td2;

	@Transactional
	public void savetestBean(Product t) {
		ts1.save(t);
	}

	@Transactional
	public void savetestBean2(Product t) {
		Product tb = new Product();
		int i = 1 / 0;
		ts1.save(t);
	}

	@Transactional
	public void savetestBean3(Product t) {
		ts1.save(t);
	}

	
	/**
	 * 直接注入数据源2的dao层就不收这个事务控制了
	 * @param t
	 */
	@Transactional
	public void savetestBean4(Product t) {
		int i = 1 / 0;
		ts1.save(t);
	}
}
