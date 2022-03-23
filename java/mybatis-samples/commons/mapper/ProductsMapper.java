package mybatis.mapper;

import mybatis.entity.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品表 Mapper 接口
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {

}
