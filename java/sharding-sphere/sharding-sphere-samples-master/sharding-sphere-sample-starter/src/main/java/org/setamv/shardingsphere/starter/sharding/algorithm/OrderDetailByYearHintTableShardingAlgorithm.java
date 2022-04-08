package org.setamv.shardingsphere.starter.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.HashSet;

/**
 * 订单明细通过Hint实现的分片算法。
 * 订单明细表本身不存订单日期，因为订单明细和订单主表都需要通过日期进行分片，所以订单明细表通过Hint的方式做强制路由
 */
public class OrderDetailByYearHintTableShardingAlgorithm<T extends Comparable<?>> implements HintShardingAlgorithm<T> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<T> shardingValue) {
        Collection<String> shardingTables = new HashSet<>();
        for (T shardingDate : shardingValue.getValues()) {
            Integer shardingYear = AlgorithmUtils.parseYear(shardingDate);
            String year = String.valueOf(shardingYear);
            for (String availableTargetName : availableTargetNames) {
                if (availableTargetName.endsWith(year)) {
                    shardingTables.add(availableTargetName);
                }
            }
        }
        return shardingTables;
    }
}
