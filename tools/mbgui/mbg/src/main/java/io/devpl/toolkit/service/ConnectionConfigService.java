package io.devpl.toolkit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.devpl.toolkit.dto.TableInfo;
import io.devpl.toolkit.dto.vo.ConnectionNameVO;
import io.devpl.toolkit.entity.JdbcConnInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 111
 * @since 2023-03-16
 */
public interface ConnectionConfigService extends IService<JdbcConnInfo> {

    /**
     * 查询所有连接名称
     *
     * @return
     */
    List<ConnectionNameVO> getAllConnectionNames();

    /**
     * 获取连接下的所有数据库名称
     *
     * @param connectionName 连接名称
     * @return 所有数据库名称
     */
    List<String> getAllDbNamesByConnectionName(String connectionName);

    /**
     * 获取连接下的所有数据库名称
     *
     * @param connectionName 连接名称
     * @param dbName         数据库名称
     * @return 所有数据库名称
     */
    List<TableInfo> getAllTables(String connectionName, String dbName);
}
