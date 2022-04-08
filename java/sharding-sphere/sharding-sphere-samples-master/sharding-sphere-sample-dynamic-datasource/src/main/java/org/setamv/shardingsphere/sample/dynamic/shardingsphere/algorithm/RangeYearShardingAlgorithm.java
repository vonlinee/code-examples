package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于年的范围分片算法。分片后缀：_yyyy，如：_2021
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class RangeYearShardingAlgorithm<T extends Comparable<?>> implements RangeShardingAlgorithm<T> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<T> shardingValue) {
        Set<String> matchedTargetNames = new HashSet<>(availableTargetNames.size(), 1.0F);
        T lowerEndPoint = shardingValue.getValueRange().lowerEndpoint();
        T upperEndPoint = shardingValue.getValueRange().upperEndpoint();

        Integer lowerYear = lowerEndPoint == null ? null : YearMonth.parse(lowerEndPoint).getYear();
        Integer upperYear = upperEndPoint == null ? null : YearMonth.parse(upperEndPoint).getYear();

        // 遍历分片的表集合，找到匹配本次分片条件的表
        for (String targetName : availableTargetNames) {
            Integer targetYear = ShardingAlgorithmUtils.parseYearFromSuffix(targetName);
            if (targetYear == null) {
                continue;
            }
            // 如果目标表所在年月 处于 lowerYearMonth 和 upperYearMonth 之间，表示被分片条件命中（lowerYearMonth 或 upperYearMonth 为空表示不限定下限或上限）
            boolean lowerMatched = lowerYear == null || lowerYear <= targetYear;
            boolean upperMatched = upperYear == null || upperYear >= targetYear;
            if (lowerMatched && upperMatched) {
                matchedTargetNames.add(targetName);
            }
        }

        return matchedTargetNames;
    }
}
