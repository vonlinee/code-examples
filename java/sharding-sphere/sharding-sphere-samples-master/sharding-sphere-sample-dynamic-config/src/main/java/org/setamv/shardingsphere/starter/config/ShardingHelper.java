package org.setamv.shardingsphere.starter.config;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * sharding jdbc分片规则Builder
 * @author setamv
 */
public class ShardingHelper {

    public static ShardingRuleConfiguration buildShardingRuleConfig() {
        ShardingRuleConfiguration ruleConfiguration = new ShardingRuleConfiguration();

        Collection<TableRuleConfiguration> tableRuleConfigurations = new LinkedList<>();
        tableRuleConfigurations.add(buildTableRuleConfig("order_main", "order_id", "order_id",
                "ds0.order_main_${[0,2]},ds1.order_main_${[1,3]}",
                "ds${order_id % 2}",
                "order_main_${order_id % 4}"));
        tableRuleConfigurations.add(buildTableRuleConfig("order_detail", "id", "order_id",
                "ds0.order_detail_${[0,2]},ds1.order_detail_${[1,3]}",
                "ds${order_id % 2}",
                "order_detail_${order_id % 4}"));
        ruleConfiguration.setTableRuleConfigs(tableRuleConfigurations);

        Collection<String> bindingTableGroups = new LinkedList<>();
        bindingTableGroups.add("order_main, order_detail");
        ruleConfiguration.setBindingTableGroups(bindingTableGroups);

        return ruleConfiguration;
    }

    public static TableRuleConfiguration buildTableRuleConfig(
            String tableName,
            String primaryColumn,
            String shardingColumn,
            String actualDataNodes,
            String databaseShardingAlgorithmExp,
            String tableShardingAlgorithmExp) {
        TableRuleConfiguration tableRuleConfiguration =
                new TableRuleConfiguration(tableName, actualDataNodes);
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(
                shardingColumn,
                tableShardingAlgorithmExp));
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration(
                shardingColumn,
                databaseShardingAlgorithmExp));
        tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", primaryColumn));
        return tableRuleConfiguration;
    }
}
