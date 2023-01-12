package io.devpl.codegen.mbpg.query;

import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.builder.CodeGenConfiguration;
import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.config.querys.DbQueryDecorator;
import io.devpl.codegen.mbpg.jdbc.DatabaseMetaDataWrapper;
import io.devpl.codegen.mbpg.util.StringPool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库查询抽象类
 * @author nieqiurong
 * @since 3.5.3
 */
public abstract class AbstractDatabaseIntrospector implements DatabaseIntrospector {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected final CodeGenConfiguration configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    protected final StrategyConfig strategyConfig;

    protected final GlobalConfig globalConfig;

    protected final DbQueryDecorator dbQuery;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    public AbstractDatabaseIntrospector(@NotNull CodeGenConfiguration configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
        this.globalConfig = configBuilder.getGlobalConfig();
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig);
    }

    @NotNull
    public CodeGenConfiguration getConfigBuilder() {
        return configBuilder;
    }

    @NotNull
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    protected void filter(List<TableInfo> tableList, List<TableInfo> includeTableList, List<TableInfo> excludeTableList) {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        if (isExclude || isInclude) {
            Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                    .stream()
                    .filter(s -> !CodeGenConfiguration.matcherRegTable(s))
                    .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                if (notExistTables.isEmpty()) {
                    break;
                }
                // 解决可能大小写不敏感的情况导致无法移除掉
                notExistTables.remove(tabInfo.getName().toLowerCase());
            }
            if (notExistTables.size() > 0) {
                LOGGER.warn("表[{}]在数据库中不存在！！！", String.join(StringPool.COMMA, notExistTables.values()));
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
