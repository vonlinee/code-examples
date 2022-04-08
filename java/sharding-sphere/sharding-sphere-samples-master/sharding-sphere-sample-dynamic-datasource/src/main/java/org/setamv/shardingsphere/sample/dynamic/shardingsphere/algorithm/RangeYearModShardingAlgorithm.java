package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于按年取模的范围分片算法。年份 % {@link #MOD}。
 * <p>如果指定的日期范围内存在奇数年，返回 _1 结尾的分片值；如果指定的日期范围内存在偶数年，返回 _2 结尾的分片值；
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class RangeYearModShardingAlgorithm<T extends Comparable<?>> implements RangeShardingAlgorithm<T> {

    /**
     * 模数。如果模数为2，表示分片的规则为年份 % 2
     */
    private static final int MOD = 2;
    private static final int MOD_LEN = 1;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<T> shardingValue) {
        Set<String> matchedTargetNames = new HashSet<>(availableTargetNames.size(), 1.0F);

        T lowerEndPoint = shardingValue.getValueRange().lowerEndpoint();
        T upperEndPoint = shardingValue.getValueRange().upperEndpoint();

        Integer lowerYear = lowerEndPoint == null ? null : YearMonth.parse(lowerEndPoint).getYear();
        Integer upperYear = upperEndPoint == null ? null : YearMonth.parse(upperEndPoint).getYear();

        Set<String> matchedMods = new HashSet<>(MOD, 1.0F);
        // 根据分片日期的范围值，计算本次可以匹配的模数
        // 如果只指定一个边界条件，匹配模数范围内的所有值
        if (lowerYear == null || upperYear == null) {
            for (int i = 0; i < MOD; i++) {
                String formattedMod = String.format("%0" + MOD_LEN + "d", i);
                matchedMods.add(formattedMod);
            }
        } else {
            for (int i = lowerYear; i <= upperYear; i++) {
                String formattedMod = String.format("%0" + MOD_LEN + "d", i % MOD);
                matchedMods.add(formattedMod);
            }
        }

        // 遍历分片集合，找到分片名称的年份后缀 % MOD后匹配的
        for (String availableTargetName : availableTargetNames) {
            int targetNameLen = availableTargetName.length();
            if (targetNameLen < MOD_LEN) {
                continue;
            }
            String modSuffix = availableTargetName.substring(targetNameLen - MOD_LEN, targetNameLen);
            if (matchedMods.contains(modSuffix)) {
                matchedTargetNames.add(availableTargetName);
            }
        }

        return matchedTargetNames;
    }




}
