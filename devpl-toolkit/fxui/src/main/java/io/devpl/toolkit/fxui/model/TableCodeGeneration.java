package io.devpl.toolkit.fxui.model;

import lombok.Data;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.List;

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
     * 表生成选项
     */
    private TableCodeGenOption option = new TableCodeGenOption();

    /**
     * 该表名称所对应的数据库信息
     */
    private DatabaseInfo databaseInfo;

    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;
}
