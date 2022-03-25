package com.mzd.multipledatasources.mapper.productcenter;

import org.springframework.stereotype.Repository;

import com.mzd.multipledatasources.entity.Product;

@Repository
public interface ProductMapper {
	void save(Product t);
}
