package com.mzd.multipledatasources.mapper.productcenter;

import org.springframework.stereotype.Repository;

import com.mzd.multipledatasources.bean.TestBean;

@Repository
public interface TransactionMapping1 {
	void save(TestBean t);
}
