package multidatasource.mapper.productcenter;

import org.springframework.stereotype.Repository;

import multidatasource.entity.Product;

@Repository
public interface ProductMapper {
	void save(Product t);
}
