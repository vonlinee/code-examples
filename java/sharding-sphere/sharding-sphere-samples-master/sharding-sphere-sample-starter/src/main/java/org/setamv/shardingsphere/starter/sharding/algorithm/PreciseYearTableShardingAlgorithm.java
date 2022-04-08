package org.setamv.shardingsphere.starter.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 基于年的分片算法
 */
public class PreciseYearTableShardingAlgorithm<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
        String year = String.valueOf(AlgorithmUtils.parseYear(shardingValue.getValue()));
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(year)) {
                return availableTargetName;
            }
        }
        return null;
    }
}
