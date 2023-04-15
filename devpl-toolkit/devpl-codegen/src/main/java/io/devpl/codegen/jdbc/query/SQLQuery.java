package io.devpl.codegen.jdbc.query;

//import io.devpl.codegen.api.Context;
//import io.devpl.codegen.api.TypeMapping;
//import io.devpl.codegen.api.IntrospectedTable;
//import io.devpl.codegen.api.TableColumn;
//import io.devpl.codegen.generator.template.impl.EntityTemplateArguments;
//import io.devpl.codegen.jdbc.DbType;
//import io.devpl.codegen.jdbc.MetaInfo;
//import io.devpl.codegen.jdbc.meta.Column;
//import io.devpl.codegen.mbpg.config.IDbQuery;
//import io.devpl.codegen.mbpg.config.querys.H2Query;
//import io.devpl.codegen.mbpg.config.rules.DataType;
//import io.devpl.sdk.util.StringUtils;
//
//import java.sql.SQLException;
//import java.util.*;

/**
 * 这是兼容以前旧版本提供的查询方式，需要每个数据库对接适配。
 */
public class SQLQuery /* extends AbstractDatabaseIntrospector */ {
//
//    public SQLQuery(Context context) {
//        super(context);
//    }
//
//    @Override
//    public List<IntrospectedTable> introspecTables() {
//        boolean isInclude = strategyConfig.getInclude().size() > 0;
//        boolean isExclude = strategyConfig.getExclude().size() > 0;
//        // 所有的表信息
//        List<IntrospectedTable> tableList = new ArrayList<>();
//        // 需要反向生成或排除的表信息
//        List<IntrospectedTable> includeTableList = new ArrayList<>();
//        List<IntrospectedTable> excludeTableList = new ArrayList<>();
//        try {
//            dbQuery.execute(dbQuery.tablesSql(), result -> {
//                String tableName = result.getStringResult(dbQuery.tableName());
//                if (StringUtils.isNotBlank(tableName)) {
//                    IntrospectedTable tableInfo = new IntrospectedTable(this.context, tableName);
//                    String tableComment = result.getTableComment();
//                    // 跳过视图
//                    if (!(strategyConfig.isSkipView() && tableComment.toUpperCase().contains("VIEW"))) {
//                        tableInfo.setComment(tableComment);
//                        if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
//                            includeTableList.add(tableInfo);
//                        } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
//                            excludeTableList.add(tableInfo);
//                        }
//                        tableList.add(tableInfo);
//                    }
//                }
//            });
//            filter(tableList, includeTableList, excludeTableList);
//            // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
//            tableList.forEach(this::convertTableFields);
//            return tableList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            // 数据库操作完成,释放连接对象
//            dbQuery.closeConnection();
//        }
//    }
//
//    @Override
//    public void convertTableFields(IntrospectedTable tableInfo) {
//        DbType dbType = this.dataSourceConfig.getDbType();
//        String tableName = tableInfo.getName();
//        try {
//            Map<String, Column> columnsInfoMap = databaseMetaDataWrapper.getColumnsInfo(tableName, false);
//            String tableFieldsSql = dbQuery.tableFieldsSql(tableName);
//            Set<String> h2PkColumns = new HashSet<>();
//            if (DbType.H2 == dbType) {
//                dbQuery.execute(String.format(H2Query.PK_QUERY_SQL, tableName), result -> {
//                    String primaryKey = result.getStringResult(dbQuery.fieldKey());
//                    if (Boolean.parseBoolean(primaryKey)) {
//                        h2PkColumns.add(result.getStringResult(dbQuery.fieldName()));
//                    }
//                });
//            }
//            EntityTemplateArguments entity = strategyConfig.entityArguments();
//            dbQuery.execute(tableFieldsSql, result -> {
//                String columnName = result.getStringResult(dbQuery.fieldName());
//                TableColumn field = new TableColumn(this.context, columnName);
//                Column column = columnsInfoMap.get(columnName.toLowerCase());
//                MetaInfo metaInfo = new MetaInfo(column);
//                // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
//                boolean isId = DbType.H2 == dbType ? h2PkColumns.contains(columnName) : result.isPrimaryKey();
//                // 处理ID
//                if (isId) {
//                    field.primaryKey(dbQuery.isKeyIdentity(result.getResultSet()));
//                    tableInfo.setHavePrimaryKey(true);
//                    if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
//                        LOGGER.warn("当前表[{}]的主键为自增主键，会导致全局主键的ID类型设置失效!", tableName);
//                    }
//                }
//                // 处理ID
//                field.setColumnName(columnName)
//                        .setType(result.getStringResult(dbQuery.fieldType()))
//                        .setComment(result.getFiledComment())
//                        .setCustomMap(dbQuery.getCustomFields(result.getResultSet()));
//                String propertyName = entity.getNameConvert().propertyNameConvert(field.getName());
//                DataType columnType = dataSourceConfig.getTypeConvert()
//                        .processTypeConvert(globalConfig, field.getType());
//                field.setPropertyName(propertyName, columnType);
//
//                tableInfo.addField(field);
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        tableInfo.processTable();
//    }
}
