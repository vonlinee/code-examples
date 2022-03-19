package mybatis.service.impl;

import mybatis.entity.Products;
import mybatis.mapper.ProductsMapper;
import mybatis.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

}
