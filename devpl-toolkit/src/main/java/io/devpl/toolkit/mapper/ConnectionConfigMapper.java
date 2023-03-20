package io.devpl.toolkit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.devpl.toolkit.dto.vo.ConnectionNameVO;
import io.devpl.toolkit.entity.ConnectionConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 111
 * @since 2023-03-16
 */
@Mapper
public interface ConnectionConfigMapper extends BaseMapper<ConnectionConfig> {

    List<ConnectionNameVO> selectAllConnectionNames();

    @Select(value = "SELECT * FROM connection_config WHERE `name` = #{connectionName}")
    ConnectionConfig selectByConnectionName(String connectionName);
}
