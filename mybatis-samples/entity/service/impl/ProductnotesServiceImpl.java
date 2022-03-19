package mybatis.service.impl;

import mybatis.entity.Productnotes;
import mybatis.mapper.ProductnotesMapper;
import mybatis.service.IProductnotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品描述表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class ProductnotesServiceImpl extends ServiceImpl<ProductnotesMapper, Productnotes> implements IProductnotesService {

}
