package org.setamv.shardingsphere.starter.sharding.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDate;
import java.util.*;

/**
 * 基于年的分片算法。如果年份小于2020年，返回ds0；否则，返回ds1
 */
public class RangeYearDatabaseShardingAlgorithm<T extends Comparable<?>> implements RangeShardingAlgorithm<T> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<T> shardingValue) {
        Set<String> shardingDataSources = new HashSet<>();
        Range<T> range = shardingValue.getValueRange();
        int startYear = range.hasLowerBound() ? AlgorithmUtils.parseYear(range.lowerEndpoint()) : ShardingConstant.SHARDING_YEAR_START;
        int endYear = range.hasUpperBound() ? AlgorithmUtils.parseYear(range.upperEndpoint()) : ShardingConstant.SHARDING_YEAR_END;

        for (int year = startYear; year <= endYear; year++) {
            if (year < 2020) {
                shardingDataSources.add("ds0");
            } else {
                shardingDataSources.add("ds1");
            }
        }
        return shardingDataSources;
    }
}
