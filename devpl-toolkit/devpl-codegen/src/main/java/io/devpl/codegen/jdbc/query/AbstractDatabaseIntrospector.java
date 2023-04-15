package io.devpl.codegen.jdbc.query;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.api.IntrospectedTable;
import io.devpl.codegen.jdbc.meta.DatabaseMetaDataHolder;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.StrategyConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDatabaseIntrospector implements DatabaseIntrospector {

    protected final Context context;

    protected final DataSourceConfig dataSourceConfig;

    protected final StrategyConfig strategyConfig;

    protected final ProjectConfiguration globalConfig;

    /**
     * 是否跳过视图
     */
    protected boolean skipView;

    protected final DatabaseMetaDataHolder databaseMetaDataWrapper;

    public AbstractDatabaseIntrospector(Context context) {
        this.context = context;
        this.dataSourceConfig = context.getDataSourceConfig();
        this.strategyConfig = context.getStrategyConfig();
        skipView = strategyConfig.isSkipView();
        this.globalConfig = context.getProjectConfiguration();
        this.databaseMetaDataWrapper = new DatabaseMetaDataHolder();
    }

    /**
     * 过滤表
     * @param tableList
     * @param includeTableList
     * @param excludeTableList
     */
    protected void filter(List<IntrospectedTable> tableList, List<IntrospectedTable> includeTableList, List<IntrospectedTable> excludeTableList) {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        if (isExclude || isInclude) {
            Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                    .stream().filter(s -> !Context.matcherRegTable(s))
                    .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (IntrospectedTable tabInfo : tableList) {
                if (notExistTables.isEmpty()) {
                    break;
                }
                // 解决可能大小写不敏感的情况导致无法移除掉
                notExistTables.remove(tabInfo.getName().toLowerCase());
            }
            if (notExistTables.size() > 0) {

            }
            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
            } else {
                tableList.clear();
                tableList.addAll(includeTableList);
            }
        }
    }
}
