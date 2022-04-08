package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 数据库分片算法。目前不分库
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class PreciseYearShardingAlgorithm<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
        YearMonth yearMonth = YearMonth.parse(shardingValue.getValue());
        String year = yearMonth.toFormattedYear();
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(year)) {
                return availableTargetName;
            }
        }
        return null;
    }
}
