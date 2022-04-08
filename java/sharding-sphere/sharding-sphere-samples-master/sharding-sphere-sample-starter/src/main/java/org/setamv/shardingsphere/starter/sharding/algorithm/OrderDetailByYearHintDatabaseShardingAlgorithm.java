package org.setamv.shardingsphere.starter.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.HashSet;

/**
 * 订单明细通过Hint实现的分片算法。
 * 订单明细表本身不存订单日期，因为订单明细和订单主表都需要通过日期进行分片，所以订单明细表通过Hint的方式做强制路由
 * 注意，对于订单日期是个区间的查询条件，需要返回多个分片值。例如：对于条件 “2019-01-01 <= 订单日期 <= 2021-04-03”，返回的数据库分片值应该是 [2019, 2020, 2021]
 */
public class OrderDetailByYearHintDatabaseShardingAlgorithm<T extends Comparable<?>> implements HintShardingAlgorithm<T> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<T> shardingValue) {
        Collection<String> shardingDataSources = new HashSet<>();
        for (T shardingDate : shardingValue.getValues()) {
            String shardingDataSource = parseTargetName(availableTargetNames, AlgorithmUtils.parseYear(shardingDate));
            if (shardingDataSource == null) {
                throw new IllegalArgumentException(String.format("找不到日期[%s]对应的数据源分片", shardingDate));
            }
            shardingDataSources.add(shardingDataSource);
        }
        return shardingDataSources;
    }

    /**
     * 返回和分片条件匹配的数据源。
     * 数据源为ds0和ds1，其中ds0包含2020年以前的分表；ds1包含2020年及以后的分表
     */
    private String parseTargetName(Collection<String> availableTargetNames, int shardingYear) {
        if (shardingYear < 2020) {
            // 2000年以前的数据划到库0
            for (String availableTargetName : availableTargetNames) {
                if (availableTargetName.endsWith("0")) {
                    return availableTargetName;
                }
            }
        } else {
            // 2000年以后的数据划到库1
            for (String availableTargetName : availableTargetNames) {
                if (availableTargetName.endsWith("1")) {
                    return availableTargetName;
                }
            }
        }
        return null;
    }

}
