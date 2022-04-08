package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 基于月的精确分片算法。分片后缀：yyyy_mm，如：2021_02
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class PreciseMonthShardingAlgorithm<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
        YearMonth yearMonth = YearMonth.parse(shardingValue.getValue());
        String formattedYearMonth = yearMonth.toFormattedMonth();
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(formattedYearMonth)) {
                return availableTargetName;
            }
        }
        return null;
    }
}
