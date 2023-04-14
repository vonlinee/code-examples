package io.devpl.codegen.jdbc.query;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.api.IntrospectedTable;
import io.devpl.codegen.jdbc.MetaInfo;
import io.devpl.codegen.mbpg.config.INameConvert;
import io.devpl.codegen.mbpg.config.po.TableField;
import io.devpl.codegen.mbpg.config.rules.DataType;
import io.devpl.codegen.jdbc.meta.DatabaseMetaDataWrapper;
import io.devpl.codegen.generator.template.impl.EntityTemplateArguments;
import io.devpl.codegen.mbpg.ITypeConvertHandler;
import io.devpl.codegen.api.TypeRegistry;
import io.devpl.sdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 元数据查询数据库信息.
 * @author nieqiurong 2022/5/11.
 * @see ITypeConvertHandler 类型转换器(如果默认逻辑不能满足，可实现此接口重写类型转换)
 * <p>
 * 测试通过的数据库：H2、Mysql-5.7.37、Mysql-8.0.25、PostgreSQL-11.15、PostgreSQL-14.1、Oracle-11.2.0.1.0、DM8
 * </p>
 * <p>
 * FAQ:
 * 1.Mysql无法读取表注释: 链接增加属性 remarks=true&useInformationSchema=true 或者通过{@link DataSourceConfig.Builder#addConnectionProperty(String, String)}设置
 * 2.Oracle无法读取注释: 增加属性remarks=true，也有些驱动版本说是增加remarksReporting=true {@link DataSourceConfig.Builder#addConnectionProperty(String, String)}
 * </p>
 * @since 3.5.3
 */
public class DefaultDatabaseIntrospector extends AbstractDatabaseIntrospector {

    private final TypeRegistry typeRegistry;

    public DefaultDatabaseIntrospector(Context context) {
        super(context);
        typeRegistry = new TypeRegistry(context.getGlobalConfig());
    }

    @Override
    public List<IntrospectedTable> introspecTables() {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        // 所有的表信息
        List<IntrospectedTable> tableList = new ArrayList<>();
        List<DatabaseMetaDataWrapper.Table> tables = getTables();
        // 需要反向生成或排除的表信息
        List<IntrospectedTable> includeTableList = new ArrayList<>();
        List<IntrospectedTable> excludeTableList = new ArrayList<>();
        tables.forEach(table -> {
            String tableName = table.getName();
            if (StringUtils.isNotBlank(tableName)) {
                IntrospectedTable tableInfo = new IntrospectedTable(this.context, tableName);
                tableInfo.setComment(table.getRemarks());
                if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                    includeTableList.add(tableInfo);
                } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                    excludeTableList.add(tableInfo);
                }
                tableList.add(tableInfo);
            }
        });
        filter(tableList, includeTableList, excludeTableList);
        // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
        tableList.forEach(this::convertTableFields);
        return tableList;
    }

    protected List<DatabaseMetaDataWrapper.Table> getTables() {
        // 是否跳过视图
        boolean skipView = strategyConfig.isSkipView();
        // 获取表过滤
        String tableNamePattern = null;
        if (strategyConfig.getLikeTable() != null) {
            tableNamePattern = strategyConfig.getLikeTable().getValue();
        }
        return databaseMetaDataWrapper.getTables(tableNamePattern, skipView ? new String[]{"TABLE"} : new String[]{"TABLE", "VIEW"});
    }

    /**
     * 转换数据库字段
     * @param tableInfo 表信息
     */
    protected void convertTableFields(IntrospectedTable tableInfo) {
        String tableName = tableInfo.getName();
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfoMap = getColumnsInfo(tableName);
        EntityTemplateArguments entity = strategyConfig.entity();
        columnsInfoMap.forEach((k, columnInfo) -> {
            MetaInfo metaInfo = new MetaInfo(columnInfo);
            String columnName = columnInfo.getName();
            TableField field = new TableField(this.context, columnName);
            // 处理ID
            if (columnInfo.isPrimaryKey()) {
                field.primaryKey(columnInfo.isAutoIncrement());
                tableInfo.setHavePrimaryKey(true);
                if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
                    LOGGER.warn("当前表[{}]的主键为自增主键，会导致全局主键的ID类型设置失效!", tableName);
                }
            }
            field.setColumnName(columnName).setComment(columnInfo.getRemarks());
            DataType columnType = typeRegistry.getColumnType(metaInfo);
            ITypeConvertHandler typeConvertHandler = dataSourceConfig.getTypeConvertHandler();
            if (typeConvertHandler != null) {
                columnType = typeConvertHandler.convert(globalConfig, typeRegistry, metaInfo);
            }
            INameConvert nameConvert = entity.getNameConvert();
            if (nameConvert != null) {
                String propertyName = nameConvert.propertyNameConvert(field);
                field.setPropertyName(propertyName, columnType);
            }
            field.setMetaInfo(metaInfo);
            tableInfo.addField(field);
        });
        tableInfo.processTable();
    }

    protected Map<String, DatabaseMetaDataWrapper.Column> getColumnsInfo(String tableName) {
        return databaseMetaDataWrapper.getColumnsInfo(tableName, true);
    }
}
