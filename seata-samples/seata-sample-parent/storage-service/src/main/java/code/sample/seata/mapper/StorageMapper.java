package code.sample.seata.mapper;

import code.sample.seata.entity.Storage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface StorageMapper extends Mapper<Storage> {

    @Update("update tab_storage set total = total - #{currentUsed}, used = used + #{currentUsed}} where product_id = #{product_id}}")
    int updateUsed(@Param("productId") long productId, @Param("currentUsed") long currentUsed);
}
 