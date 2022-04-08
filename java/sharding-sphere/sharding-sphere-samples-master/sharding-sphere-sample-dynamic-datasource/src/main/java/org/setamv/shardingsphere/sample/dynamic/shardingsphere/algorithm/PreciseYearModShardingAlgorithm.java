package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 基于年的数据库精确分片算法。按年%2
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class PreciseYearModShardingAlgorithm<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
        YearMonth yearMonth = YearMonth.parse(shardingValue.getValue());
        String mod = String.valueOf(yearMonth.getYear() % 2);
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(mod)) {
                return availableTargetName;
            }
        }
        return null;
    }
}
