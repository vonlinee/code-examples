package multidatasource.dao.productcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import multidatasource.entity.Product;
import multidatasource.mapper.productcenter.ProductMapper;

@Component

public class ProductDao {
	@Autowired
	private ProductMapper tm1;

	public void save(Product t) {
		
	}
}
