package org.setamv.shardingsphere.starter.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 基于年的分片算法
 */
public class PreciseYearDatabaseShardingAlgorithm<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
        int year = AlgorithmUtils.parseYear(shardingValue.getValue());
        return year < 2020 ? "ds0" : "ds1";
    }
}
