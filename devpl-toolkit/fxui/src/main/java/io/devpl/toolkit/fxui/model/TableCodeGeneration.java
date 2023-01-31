package io.devpl.toolkit.fxui.model;

import java.util.List;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import lombok.Data;

/**
 * 长驻内存：每个表对应一个单例
 */
@Data
public class TableCodeGeneration {

    private Integer id;
    private String dbName;
    private String tableName;
    private String tableComment;

    /**
     * 连接配置
     */
    private ConnectionConfig connectionInfo;

    /**
     * 表生成选项
     */
    private TableCodeGenOption option = new TableCodeGenOption();

    /**
     * 该表名称所对应的数据库信息
     */
    private DatabaseInfo databaseInfo;

    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;

    /**
     * 唯一标识符
     * @return
     */
    public String getUniqueKey() {
        return dbName + "." + tableName;
    }
}
