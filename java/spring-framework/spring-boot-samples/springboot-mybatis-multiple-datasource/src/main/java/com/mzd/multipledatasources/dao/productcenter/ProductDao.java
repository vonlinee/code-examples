package com.mzd.multipledatasources.dao.productcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mzd.multipledatasources.entity.Product;
import com.mzd.multipledatasources.mapper.productcenter.ProductMapper;

@Component

public class ProductDao {
	@Autowired
	private ProductMapper tm1;

	public void save(Product t) {
		
	}
}
