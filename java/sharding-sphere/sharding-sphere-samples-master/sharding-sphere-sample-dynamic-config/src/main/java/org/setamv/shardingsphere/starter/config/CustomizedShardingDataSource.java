package org.setamv.shardingsphere.starter.config;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import org.apache.shardingsphere.spi.NewInstanceServiceLoader;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.apache.shardingsphere.underlying.merge.engine.ResultProcessEngine;
import org.apache.shardingsphere.underlying.rewrite.context.SQLRewriteContextDecorator;
import org.apache.shardingsphere.underlying.route.decorator.RouteDecorator;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 定制化Sharding JDBC的{@link org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource}
 * <p>因为{@link org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource}里面的runtimeContext是final的，
 * 而runtimeContext里面包含了sharding jdbc分片规则信息，如果需要在运行时动态设置sharding jdbc的分片规则，只能重新定义该类。
 * <p>该类只是在{@link org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource}的基础上增加了运行时动态修改runtimeContext的逻辑。
 * @author setamv
 */
@Getter
public class CustomizedShardingDataSource extends AbstractDataSourceAdapter {

    private Properties props;
    private ShardingRuntimeContext runtimeContext;

    static {
        NewInstanceServiceLoader.register(RouteDecorator.class);
        NewInstanceServiceLoader.register(SQLRewriteContextDecorator.class);
        NewInstanceServiceLoader.register(ResultProcessEngine.class);
    }

    public CustomizedShardingDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRuleConfiguration shardingRuleConfig, final Properties props) throws SQLException {
        super(dataSourceMap);
        this.props = props;
        checkDataSourceType(dataSourceMap);
        ShardingRule shardingRule = new ShardingRule(shardingRuleConfig, dataSourceMap.keySet());
        runtimeContext = new ShardingRuntimeContext(dataSourceMap, shardingRule, props, getDatabaseType());
    }

    private void checkDataSourceType(final Map<String, DataSource> dataSourceMap) {
        for (DataSource each : dataSourceMap.values()) {
            Preconditions.checkArgument(!(each instanceof MasterSlaveDataSource), "Initialized data sources can not be master-slave data sources.");
        }
    }

    @Override
    public final ShardingConnection getConnection() {
        return new ShardingConnection(getDataSourceMap(), runtimeContext, TransactionTypeHolder.get());
    }

    /**
     * 刷新分片规则
     * @param shardingRuleConfig
     * @throws SQLException
     */
    public synchronized void refreshShardingRule(final ShardingRuleConfiguration shardingRuleConfig) throws SQLException {
        ShardingRule shardingRule = new ShardingRule(shardingRuleConfig, getDataSourceMap().keySet());
        ShardingRuntimeContext newRuntimeContext = new ShardingRuntimeContext(getDataSourceMap(), shardingRule, props, getDatabaseType());
        this.runtimeContext = newRuntimeContext;
    }
}