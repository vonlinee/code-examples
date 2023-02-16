package io.devpl.tookit.fxui.model;

import io.devpl.tookit.utils.StringUtils;
import lombok.Data;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.List;

/**
 * 长驻内存：每个表对应一个单例
 * 表生成配置
 */
@Data
public class TableCodeGeneration {

    /**
     * 数据库连接名称
     */
    private String connectionName;

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
     * 表生成选项
     */
    private TableCodeGenOption option = new TableCodeGenOption();

    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;

    /**
     * 唯一标识符
     *
     * @return 唯一标识符  连接
     */
    public String getUniqueKey() {
        if (uniqueKey == null) {
            uniqueKey = connectionName + "#" + databaseName + "#" + tableName;
        }
        return uniqueKey;
    }

    public String getMapperName() {
        String tableNameCamel = StringUtils.underlineToCamel(tableName);
        tableNameCamel = StringUtils.upperFirst(tableNameCamel);
        return tableNameCamel + "Mapper";
    }

    public String getDomainObjectName() {
        String tableNameCamel = StringUtils.underlineToCamel(tableName);
        tableNameCamel = StringUtils.upperFirst(tableNameCamel);
        return tableNameCamel;
    }
}
