package io.devpl.toolkit.fxui.model;

import java.util.List;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import lombok.Data;

/**
 * 长驻内存：每个表对应一个单例
 * 表生成配置
 */
@Data
public class TableCodeGenConfig {

    /**
     * 该表所在的数据库
     */
    private String databaseName;

    /**
     * 该表名称
     */
    private String tableName;
    private String uniqueKey;

    /**
     * 数据库连接名称
     */
    private String connectionName;

    /**
     * 表生成选项
     */
    private TableCodeGenOption option = new TableCodeGenOption();

    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;

    /**
     * 唯一标识符
     * @return 唯一标识符  连接
     */
    public String getUniqueKey() {
        if (uniqueKey == null) {
            uniqueKey = connectionName + "#" + databaseName + "#" + tableName;
        }
        return uniqueKey;
    }
}
