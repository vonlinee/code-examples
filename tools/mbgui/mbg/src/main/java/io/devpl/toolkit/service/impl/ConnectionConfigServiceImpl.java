package io.devpl.toolkit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.devpl.toolkit.codegen.JDBCDriver;
import io.devpl.toolkit.dto.TableInfo;
import io.devpl.toolkit.dto.vo.ConnectionNameVO;
import io.devpl.toolkit.entity.JdbcConnInfo;
import io.devpl.toolkit.mapper.ConnectionConfigMapper;
import io.devpl.toolkit.service.ConnectionConfigService;
import io.devpl.toolkit.utils.DBUtils;
import io.devpl.toolkit.sqlparser.meta.TableMetadata;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author devpl
 * @since 2023-03-16
 */
@Service
public class ConnectionConfigServiceImpl extends ServiceImpl<ConnectionConfigMapper, JdbcConnInfo> implements ConnectionConfigService {

    @Resource
    ConnectionConfigMapper connConfigMapper;

    @Override
    public List<ConnectionNameVO> getAllConnectionNames() {
        return connConfigMapper.selectAllConnectionNames();
    }

    @Override
    public List<String> getAllDbNamesByConnectionName(String connectionName) {

        JdbcConnInfo connectionConfig = connConfigMapper.selectByConnectionName(connectionName);
        if (connectionConfig == null) {
            throw new RuntimeException("不存在连接：" + connectionName);
        }
        JDBCDriver driver;
        try {
            driver = JDBCDriver.valueOfName(connectionConfig.getDbType());
        } catch (NullPointerException exception) {
            throw new RuntimeException("不支持数据库类型" + connectionConfig.getDbType());
        }

        String host = connectionConfig.getHost();
        String port = connectionConfig.getPort();
        String username = connectionConfig.getUsername();
        String password = connectionConfig.getPassword();

        assert driver != null;
        String connectionUrl = driver.getConnectionUrl(host, port, "", null);
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);

        List<String> databaseNames;
        try (Connection connection = DBUtils.getConnection(connectionUrl, properties)) {
            databaseNames = DBUtils.getDatabaseNames(connection);
        } catch (SQLException e) {
            throw new RuntimeException("获取连接失败", e);
        }
        return databaseNames;
    }

    @Override
    public List<TableInfo> getAllTables(String connectionName, String dbName) {
        JdbcConnInfo connectionConfig = connConfigMapper.selectByConnectionName(connectionName);
        JDBCDriver driver = JDBCDriver.valueOfName(connectionConfig.getDbType());

        String host = connectionConfig.getHost();
        String port = connectionConfig.getPort();
        String username = connectionConfig.getUsername();
        String password = connectionConfig.getPassword();
        assert driver != null;
        String connectionUrl = driver.getConnectionUrl(host, port, dbName, null);
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        try (Connection connection = DBUtils.getConnection(connectionUrl, properties)) {
            List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection);
            List<TableInfo> tableInfos = tablesMetadata.stream().map(tableMetadata -> {
                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(tableMetadata.getTableName());
                tableInfo.setComment(tableMetadata.getRemarks());
                return tableInfo;
            }).collect(Collectors.toList());
            return tableInfos;
        } catch (SQLException e) {
            throw new RuntimeException("获取连接失败", e);
        }
    }
}
